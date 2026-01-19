package cn.ibenbeni.bens.message.center.access.action;

import cn.ibenbeni.bens.message.center.access.model.MessageSendContext;
import cn.ibenbeni.bens.message.center.common.chain.ChainAction;

/**
 * 消息发送 Action 接口
 * 定义统一的 Action 执行器接口，采用责任链模式
 */
public interface MessageSendAction extends ChainAction<MessageSendContext> {

}
