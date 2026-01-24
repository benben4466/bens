package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.ibenbeni.bens.message.center.api.MessageSendTaskApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendTaskDTO;
import cn.ibenbeni.bens.message.center.api.constants.chain.MessageCenterChainOrderConstants;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.UserSendMessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息发送任务创建 Action
 * 职责: 生成 Batch ID 并落库 MessageSendTask
 */
@Slf4j
@Component
public class MessageSendTaskCreateAction implements MessageSendAction {

    @Resource
    private MessageSendTaskApi messageSendTaskApi;

    @Override
    public void execute(UserSendMessageContext context) {
        log.info("[MessageSendTaskCreateAction][开始创建发送任务][templateCode: {}]", context.getTemplateCode());

        // 构建消息发送任务
        MessageSendTaskDTO msgSendTask = buildMessageSendTask(context);
        // 保存入库
        Long taskId = messageSendTaskApi.createTask(msgSendTask);

        // 将 TaskID 注入上下文
        context.setTaskId(taskId);
        log.info("[MessageSendTaskCreateAction][任务创建成功][taskId: {}]", taskId);
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.AccessLayer.TASK_CREATE;
    }

    private MessageSendTaskDTO buildMessageSendTask(UserSendMessageContext context) {
        return MessageSendTaskDTO.builder()
                .taskCode(context.getBizId()) // 暂用 bizId 作为 taskCode，也可生成 UUID
                .templateCode(context.getTemplateCode())
                .taskStatus(MessageTaskStatusEnum.WAITING_SPLIT)
                .tenantId(context.getTenantId())
                .totalUserCount(0L)
                .totalMsgCount(0L)
                .build();
    }

}
