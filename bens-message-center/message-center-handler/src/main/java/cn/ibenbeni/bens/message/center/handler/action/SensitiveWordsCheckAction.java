package cn.ibenbeni.bens.message.center.handler.action;

import cn.ibenbeni.bens.message.center.handler.model.MessageHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 敏感词检查 Action
 * 当前为预留接口，默认通过检测
 */
@Slf4j
@Component
public class SensitiveWordsCheckAction implements MessageHandleAction {

    @Value("${bens.message-center.sensitive-words.enabled:true}")
    private boolean enabled;

    @Value("${bens.message-center.sensitive-words.mode:REPLACE}")
    private String mode;

    @Override
    public void execute(MessageHandleContext context) {
        if (!enabled) {
            log.debug("[SensitiveWordsCheckAction][敏感词检测已禁用]");
            return;
        }

        log.info("[SensitiveWordsCheckAction][开始敏感词检测][recordId: {}]", context.getRecordId());

        // 预留接口：当前默认通过
        // TODO: 后续可接入自定义敏感词库或第三方内容安全服务
        boolean passed = checkSensitiveWords(context.getMessageTitle())
                && checkSensitiveWords(context.getMessageContent());

        if (!passed) {
            log.warn("[SensitiveWordsCheckAction][敏感词检测不通过][recordId: {}]", context.getRecordId());
            context.setInterrupted(true);
            context.setFailReason("消息内容包含敏感词");
            return;
        }

        log.info("[SensitiveWordsCheckAction][敏感词检测通过][recordId: {}]", context.getRecordId());
    }

    @Override
    public int getOrder() {
        return 100;
    }

    /**
     * 检查敏感词（预留实现）
     *
     * @param content 内容
     * @return true=通过，false=包含敏感词
     */
    private boolean checkSensitiveWords(String content) {
        // 预留接口，当前默认返回 true（通过）
        // 后续可实现：
        // 1. 自定义敏感词库 + DFA/Trie 算法
        // 2. 接入阿里云/腾讯云内容安全服务
        return true;
    }
}
