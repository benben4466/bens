package cn.ibenbeni.bens.common.chain.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 责任链处理器
 * 负责组织和执行 Action 链，提供统一的责任链执行逻辑
 *
 * @param <T> 上下文类型，必须实现 ChainContext 接口
 * @author bens
 */
@Slf4j
@Getter
public class ChainProcessor<T extends ChainContext> {

    /**
     * Action 链
     */
    private final List<ChainAction<T>> actions;

    /**
     * 责任链名称（用于日志）
     */
    private final String chainName;

    /**
     * 构造函数
     *
     * @param actions   Action 列表
     * @param chainName 责任链名称
     */
    public ChainProcessor(List<? extends ChainAction<T>> actions, String chainName) {
        // 使用 ArrayList 进行安全的深拷贝，避免强制类型转换
        this.actions = new ArrayList<>(actions);
        this.chainName = chainName;

        // 按 order 排序
        if (!this.actions.isEmpty()) {
            this.actions.sort(Comparator.comparingInt(ChainAction::getOrder));
            log.debug("[ChainProcessor][{}][初始化完成][Action链: {}]",
                    chainName,
                    this.actions.stream()
                            .map(a -> a.getClass().getSimpleName() + "(" + a.getOrder() + ")")
                            .toArray()
            );
        }
    }

    /**
     * 执行责任链
     *
     * @param context 责任链上下文
     */
    public void execute(T context) {
        if (actions == null || actions.isEmpty()) {
            log.warn("[ChainProcessor][{}][责任链为空，无需执行]", chainName);
            return;
        }

        log.debug("[ChainProcessor][{}][开始执行责任链][Action数量: {}]", chainName, actions.size());

        for (ChainAction<T> action : actions) {
            // 检查是否中断
            if (context.isInterrupted()) {
                log.warn("[ChainProcessor][{}][责任链中断][当前Action: {}, 错误信息: {}]", chainName, action.getClass().getSimpleName(), context.getErrorMessage());
                break;
            }

            log.debug("[ChainProcessor][{}][执行Action][Action: {}, Order: {}]", chainName, action.getClass().getSimpleName(), action.getOrder());

            try {
                action.execute(context);
            } catch (Exception ex) {
                log.error("[ChainProcessor][{}][Action执行异常][Action: {}]", chainName, action.getClass().getSimpleName(), ex);
                context.interrupt("Action执行异常: " + ex.getMessage());
                break;
            }
        }

        log.debug("[ChainProcessor][{}][责任链执行完成][是否中断: {}]", chainName, context.isInterrupted());
    }

}
