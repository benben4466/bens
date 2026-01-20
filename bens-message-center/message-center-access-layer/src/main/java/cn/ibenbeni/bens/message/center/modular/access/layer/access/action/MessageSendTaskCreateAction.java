package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.ibenbeni.bens.message.center.api.MessageSendTaskApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendTaskDTO;
import cn.ibenbeni.bens.message.center.common.constants.chain.MessageCenterChainOrderConstants;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.MessageSendContext;
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
    public void execute(MessageSendContext context) {
        log.info("[MessageSendTaskCreateAction][开始创建发送任务][templateCode: {}]", context.getTemplateCode());

        // 构建任务对象
        MessageSendTaskDTO task = new MessageSendTaskDTO();
        task.setTaskCode(context.getBizId()); // 暂用 bizId 作为 taskCode，也可生成 UUID
        task.setTemplateId(context.getTemplate().getTemplateId());
        task.setTemplateCode(context.getTemplateCode());
        task.setTaskStatus(MessageTaskStatusEnum.WAITING_SPLIT);
        task.setTenantId(context.getTenantId());

        // 预估用户数（如果 recipient 中包含列表，可以在此统计；暂置为 0 由拆分层更新）
        task.setTotalUserCount(0L);
        task.setTotalMsgCount(0L);

        // 保存入库
        Long taskId = messageSendTaskApi.createTask(task);

        // 将 TaskID 注入上下文
        context.setTaskId(taskId);

        log.info("[MessageSendTaskCreateAction][任务创建成功][taskId: {}]", taskId);
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.AccessLayer.TASK_CREATE;
    }

}
