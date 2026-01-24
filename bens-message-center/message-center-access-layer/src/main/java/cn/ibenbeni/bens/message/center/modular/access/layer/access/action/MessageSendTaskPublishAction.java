package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.TaskSplitPayload;
import cn.ibenbeni.bens.message.center.api.constants.chain.MessageCenterChainOrderConstants;
import cn.ibenbeni.bens.message.center.api.constants.mq.MessageCenterMqTopicConstants;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.UserSendMessageContext;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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

    @Override
    public void execute(UserSendMessageContext context) {
        log.info("[MessageSendTaskPublishAction][开始投递任务到拆分队列][taskId: {}]", context.getTaskId());
        if (context.getInterrupted()) {
            log.error("[execute][忽略执行][责任连已中断执行][入参: {}]", JSON.toJSONString(context));
            return;
        }

        // 校验 TaskID 是否存在
        if (context.getTaskId() == null) {
            log.error("[execute][忽略执行][任务ID为空，无法投递][入参: {}]", JSON.toJSONString(context));
            context.interrupt("任务ID为空");
            return;
        }

        try {
            // 构建拆分任务 Payload
            TaskSplitPayload payload = buildTaskSplitPayload(context);

            // TODO [优化] convertAndSend 方法执行失败时，不会有任何回调和异常抛出
            // 投递到拆分队列
            rocketMQTemplate.convertAndSend(MessageCenterMqTopicConstants.SPLIT_TOPIC, JSON.toJSONString(payload));
            log.info("[execute][投递拆分任务成功][taskId: {}, 任务拆分载荷: {}]", context.getTaskId(), JSON.toJSONString(payload));
        } catch (Exception ex) {
            log.error("[execute][投递拆分任务失败][taskId: {}]", context.getTaskId(), ex);
            throw new MessageCenterException(MessageCenterExceptionEnum.MESSAGE_QUEUE_SEND_FAIL);
        }
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.AccessLayer.TASK_PUBLISH;
    }

    private TaskSplitPayload buildTaskSplitPayload(UserSendMessageContext context) {
        return TaskSplitPayload.builder()
                .bizId(context.getBizId())
                .taskId(context.getTaskId())
                .templateCode(context.getTemplateCode())
                .templateParams(context.getTemplateParams())
                .template(context.getTemplate())
                .recipientInfos(context.getRecipientInfos())
                .tenantId(context.getTenantId())
                .build();
    }

}
