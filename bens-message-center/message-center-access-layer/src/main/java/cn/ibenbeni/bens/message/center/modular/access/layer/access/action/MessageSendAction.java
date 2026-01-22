package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.ibenbeni.bens.common.chain.core.ChainAction;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.UserSendMessageContext;

/**
 * 消息发送 Action 接口
 * 定义统一的 Action 执行器接口，采用责任链模式
 */
public interface MessageSendAction extends ChainAction<UserSendMessageContext> {

}
