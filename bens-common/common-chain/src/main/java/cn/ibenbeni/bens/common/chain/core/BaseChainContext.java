package cn.ibenbeni.bens.common.chain.core;

import lombok.Data;

/**
 * 责任链上下文基础抽象类
 * 提供责任链模式中上下文对象的通用实现
 *
 * @author bens
 */
@Data
public abstract class BaseChainContext implements ChainContext {

    /**
     * 是否中断执行
     */
    private Boolean interrupted = Boolean.FALSE;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 租户ID
     */
    private Long tenantId;

    @Override
    public boolean isInterrupted() {
        return this.interrupted;
    }

    @Override
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

}
