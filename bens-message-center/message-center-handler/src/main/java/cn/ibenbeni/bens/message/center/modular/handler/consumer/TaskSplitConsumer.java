package cn.ibenbeni.bens.message.center.modular.handler.consumer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.common.core.serialization.JacksonUtils;
import cn.ibenbeni.bens.message.center.api.MessageSendDetailApi;
import cn.ibenbeni.bens.message.center.api.MessageSendTaskApi;
import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageQueuePayload;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendDetailDTO;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendTaskDTO;
import cn.ibenbeni.bens.message.center.api.domian.dto.TaskSplitPayload;
import cn.ibenbeni.bens.message.center.api.constants.mq.MessageCenterMqTopicConstants;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务拆分消费者 (Splitter)
 * 职责: 将大任务拆分为细粒度执行单元 (Detail)，分批落库并分发
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = MessageCenterMqTopicConstants.SPLIT_TOPIC,
        consumerGroup = MessageCenterMqTopicConstants.SPLIT_CONSUMER_GROUP
)
public class TaskSplitConsumer implements RocketMQListener<String> {

    // 批量插入大小
    private static final int BATCH_SIZE = 1000;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private MessageSendTaskApi messageSendTaskApi;

    @Resource
    private MessageSendDetailApi messageSendDetailApi;

    @Override
    public void onMessage(String message) {
        log.info("[TaskSplitConsumer][收到拆分任务][msg: {}]", message);
        TaskSplitPayload payload = preCheck(message);
        if (payload == null) {
            return;
        }

        TenantContextHolder.setTenantId(payload.getTenantId()); // 设置租户 ID

        // 检查消息发送任务
        if (!checkMessageSendTask(payload.getTaskId())) {
            return;
        }

        MessageSendTaskDTO updateSendTaskDTO = MessageSendTaskDTO.builder()
                .taskId(payload.getTaskId())
                .taskStatus(MessageTaskStatusEnum.PROCESSING)
                .build();
        try {
            // 更新状态为处理中
            updateSendTaskDTO.setTaskStatus(MessageTaskStatusEnum.PROCESSING);
            messageSendTaskApi.updateTask(updateSendTaskDTO);

            // 2. 拆分与落库
            List<MessageSendDetailDTO> batchDetails = new ArrayList<>();
            long totalMsgCount = 0;
            long totalUserCount = 0;

            for (AbstractRecipientInfo info : payload.getRecipientInfos()) {
                if (info.getChannelType() == null || CollUtil.isEmpty(info.getIdentifiers())) {
                    continue;
                }

                for (String identifier : info.getIdentifiers()) {
                    MessageSendDetailDTO detail = buildMessageSendDetail(payload, MsgPushChannelTypeEnum.fromCode(info.getChannelType()), identifier);
                    batchDetails.add(detail);
                    totalMsgCount++;
                    totalUserCount++;
                    // 分批落库
                    if (batchDetails.size() >= BATCH_SIZE) {
                        flushBatch(batchDetails, payload);
                        batchDetails.clear();
                    }
                }
            }

            // 冲刷剩余
            if (!batchDetails.isEmpty()) {
                flushBatch(batchDetails, payload);
                batchDetails.clear();
            }

            // 4. 更新主任务统计信息和完成状态
            updateSendTaskDTO.setTotalUserCount(totalUserCount);
            updateSendTaskDTO.setTotalMsgCount(totalMsgCount);
            messageSendTaskApi.updateTask(updateSendTaskDTO);

            log.info("[TaskSplitConsumer][任务拆分完成][taskId: {}, totalMsg: {}]", payload.getTaskId(), totalMsgCount);
        } catch (Exception ex) {
            log.error("[TaskSplitConsumer][任务拆分失败][taskId: {}]", payload.getTaskId(), ex);
            updateSendTaskDTO.setTaskStatus(MessageTaskStatusEnum.PARTIAL_FAIL);
            messageSendTaskApi.updateTask(updateSendTaskDTO);
            throw ex;
        }
    }

    private void flushBatch(List<MessageSendDetailDTO> details, TaskSplitPayload originPayload) {
        // 1. 批量保存 (调用 API)
        messageSendDetailApi.saveBatch(details);

        // 2. 分发 MQ (Execute)
        for (MessageSendDetailDTO detail : details) {
            MessageQueuePayload executePayload = buildMessageQueuePayload(originPayload, detail);
            // TODO [优化] 形成方法，不要直接拼接
            String destination = MessageCenterMqTopicConstants.EXECUTE_TOPIC + ":" + detail.getChannelType().getType();
            rocketMQTemplate.convertAndSend(destination, JSON.toJSONString(executePayload));
        }
    }

    /**
     * 前置校验
     *
     * @param message MQ消息字符串
     * @return 任务拆分载荷
     */
    private TaskSplitPayload preCheck(String message) {
        if (StrUtil.isBlank(message)) {
            log.error("[TaskSplitConsumer][忽略处理][MQ消息为空]");
            return null;
        }

        TaskSplitPayload payload = JacksonUtils.parseObject(message, TaskSplitPayload.class);
        if (payload == null || payload.getTaskId() == null) {
            log.error("[TaskSplitConsumer][忽略处理][消息体为空或TaskId缺失]");
            return null;
        }
        if (payload.getTenantId() == null) {
            log.error("[TaskSplitConsumer][忽略处理][租户ID缺失][业务ID: {}, 任务ID: {}, 载荷: {}]", payload.getBizId(), payload.getTaskId(), JSON.toJSONString(payload));
            return null;
        }
        if (CollUtil.isEmpty(payload.getRecipientInfos())) {
            log.error("[TaskSplitConsumer][忽略处理][接收者信息缺失][业务ID: {}, 任务ID: {}, 载荷: {}]", payload.getBizId(), payload.getTaskId(), JSON.toJSONString(payload));
            return null;
        }

        return payload;
    }

    /**
     * 检查消息发送任务
     *
     * @param taskId 消息发送任务 ID
     * @return true=通过；false=不通过；
     */
    private boolean checkMessageSendTask(Long taskId) {
        // 检查任务状态
        MessageSendTaskDTO task = messageSendTaskApi.getTaskById(taskId);
        if (task == null) {
            log.error("[TaskSplitConsumer][忽略处理][任务不存在][taskId: {}]", taskId);
            return false;
        }
        if (task.getTaskStatus() != MessageTaskStatusEnum.WAITING_SPLIT) {
            log.warn("[TaskSplitConsumer][忽略处理][任务状态不是待拆分,跳过][taskId: {}, status: {}]", taskId, task.getTaskStatus());
            return false;
        }

        return true;
    }

    /**
     * 构建消息发送明细
     *
     * @param payload     任务拆分载荷
     * @param channelType 渠道类型
     * @param identifier  标识值。如手机号、邮箱等
     * @return 消息发送明细 DTO
     */
    private MessageSendDetailDTO buildMessageSendDetail(TaskSplitPayload payload, MsgPushChannelTypeEnum channelType, String identifier) {
        return MessageSendDetailDTO.builder()
                .taskId(payload.getTaskId())
                .channelType(channelType)
                .recipientIdentifier(identifier)
                .msgVariables(payload.getTemplateParams())
                .sendStatus(MessageDetailStatusEnum.PENDING)
                .tenantId(payload.getTenantId())
                .build();
    }

    private MessageQueuePayload buildMessageQueuePayload(TaskSplitPayload originPayload, MessageSendDetailDTO sendDetail) {
        // TODO [优化] recipientInfos 是否应该填充 channelType 对应的 recipientInfo
        return MessageQueuePayload.builder()
                .bizId(originPayload.getBizId())
                .templateCode(originPayload.getTemplateCode())
                .channelType(sendDetail.getChannelType().getType())
                .recipientInfos(originPayload.getRecipientInfos())
                .msgVariables(sendDetail.getMsgVariables())
                .tenantId(originPayload.getTenantId())
                .build();
    }

}
