package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.TaskSplitPayload;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.MessageSendContext;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息发送任务投递 Action (接入层 -> 拆分层)
 * 职责: 将任务信息投递到拆分队列
 */
@Slf4j
@Component
public class MessageSendTaskPublishAction implements MessageSendAction {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${bens.message-center.split.topic:MESSAGE_CENTER_SPLIT_TOPIC}")
    private String splitTopic;

    @Override
    public void execute(MessageSendContext context) {
        log.info("[MessageSendTaskPublishAction][开始投递任务到拆分队列][taskId: {}]", context.getTaskId());

        // 校验 TaskID 是否存在
        if (context.getTaskId() == null) {
            log.error("[MessageSendTaskPublishAction][任务ID为空，无法投递]");
            context.interrupt("任务ID为空");
            return;
        }

        try {
            // 构建拆分任务 Payload
            TaskSplitPayload payload = new TaskSplitPayload();
            payload.setTaskId(context.getTaskId());
            payload.setTemplateCode(context.getTemplateCode());
            payload.setTemplate(context.getTemplate());
            payload.setBizId(context.getBizId());
            payload.setRecipientType(context.getRecipientType());
            payload.setRecipient(context.getRecipient());
            payload.setChannels(context.getChannels());
            payload.setTemplateParams(context.getTemplateParams());
            payload.setTenantId(context.getTenantId());

            // 投递到拆分队列
            rocketMQTemplate.convertAndSend(splitTopic, JSON.toJSONString(payload));

            log.info("[MessageSendTaskPublishAction][投递拆分任务成功][taskId: {}]", context.getTaskId());

        } catch (Exception ex) {
            log.error("[MessageSendTaskPublishAction][投递拆分任务失败][taskId: {}]", context.getTaskId(), ex);
            throw new MessageCenterException(MessageCenterExceptionEnum.MESSAGE_QUEUE_SEND_FAIL);
        }
    }

    @Override
    public int getOrder() {
        return 400;
    }

}
