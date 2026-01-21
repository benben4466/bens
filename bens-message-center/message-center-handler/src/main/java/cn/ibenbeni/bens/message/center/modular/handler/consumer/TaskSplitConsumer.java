package cn.ibenbeni.bens.message.center.modular.handler.consumer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.MessageSendDetailApi;
import cn.ibenbeni.bens.message.center.api.MessageSendTaskApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageQueuePayload;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendDetailDTO;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendTaskDTO;
import cn.ibenbeni.bens.message.center.api.pojo.dto.TaskSplitPayload;
import cn.ibenbeni.bens.message.center.common.constants.mq.MessageCenterMqTopicConstants;
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
import java.util.Map;

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
        TenantContextHolder.setTenantId(payload.getTenantId()); // 设置租户ID

        Long taskId = payload.getTaskId();

        // 1. 幂等/状态检查
        MessageSendTaskDTO task = messageSendTaskApi.getTaskById(taskId);
        if (task == null) {
            log.error("[TaskSplitConsumer][任务不存在][taskId: {}]", taskId);
            return;
        }
        if (task.getTaskStatus() != MessageTaskStatusEnum.WAITING_SPLIT) {
            log.warn("[TaskSplitConsumer][任务状态不是待拆分，跳过][taskId: {}, status: {}]", taskId, task.getTaskStatus());
            return;
        }

        try {
            // 更新状态为处理中
            task.setTaskStatus(MessageTaskStatusEnum.PROCESSING);
            messageSendTaskApi.updateTask(task);

            // 2. 解析逻辑 (确定用户列表和渠道列表)
            List<String> targetUsers = getTargetUsers(payload);
            List<Integer> channels = payload.getChannels();
            if (CollUtil.isEmpty(channels)) {
                // 如果 Payload 没传，从模板配置查 (通常 Access 层已填充，这里兜底)
                // 简化处理：假设 channels 必传或已在 Payload 中
            }

            // 3. 拆分与落库 (双重循环: 用户 x 渠道)
            List<MessageSendDetailDTO> batchDetails = new ArrayList<>();
            long totalMsgCount = 0;

            for (String user : targetUsers) {
                for (Integer channelType : channels) {
                    // 变量解析下沉到 Execute 层，这里只存原始参数，或者在这里做通用解析
                    // 假设变量针对 User 是相同的 (除了 User 本身属性)，或者 Payload 里带了用户特定参数
                    // 这里简化为：所有用户使用相同参数

                    MessageSendDetailDTO detail = new MessageSendDetailDTO();
                    detail.setTaskId(taskId);
                    detail.setTargetUser(user);

                    MsgPushChannelTypeEnum channelEnum = MsgPushChannelTypeEnum.fromCode(channelType);
                    detail.setChannelType(channelEnum);

                    detail.setMsgVariables(payload.getTemplateParams());
                    detail.setSendStatus(MessageDetailStatusEnum.PENDING);
                    detail.setTenantId(payload.getTenantId());

                    batchDetails.add(detail);
                    totalMsgCount++;

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
            task.setTotalUserCount((long) targetUsers.size());
            task.setTotalMsgCount(totalMsgCount);
            messageSendTaskApi.updateTask(task);

            log.info("[TaskSplitConsumer][任务拆分完成][taskId: {}, totalMsg: {}]", taskId, totalMsgCount);

        } catch (Exception e) {
            log.error("[TaskSplitConsumer][任务拆分失败][taskId: {}]", taskId, e);
            task.setTaskStatus(MessageTaskStatusEnum.PARTIAL_FAIL);
            messageSendTaskApi.updateTask(task);
            throw e;
        }
    }

    private List<String> getTargetUsers(TaskSplitPayload payload) {
        List<String> users = new ArrayList<>();
        Map<String, Object> recipient = payload.getRecipient();
        if (recipient != null) {
            if (recipient.containsKey("userId")) {
                users.add(String.valueOf(recipient.get("userId")));
            } else if (recipient.containsKey("phone")) {
                users.add(String.valueOf(recipient.get("phone")));
            } else if (recipient.containsKey("email")) {
                users.add(String.valueOf(recipient.get("email")));
            }
        }
        return users;
    }

    private void flushBatch(List<MessageSendDetailDTO> details, TaskSplitPayload originPayload) {
        // 1. 批量保存 (调用 API)
        messageSendDetailApi.saveBatch(details);

        // 2. 分发 MQ (Execute)
        for (MessageSendDetailDTO detail : details) {
            MessageQueuePayload executePayload = new MessageQueuePayload();
            executePayload.setRecordId(detail.getId());
            executePayload.setBizId(originPayload.getBizId());
            executePayload.setTemplateCode(originPayload.getTemplateCode());
            executePayload.setTemplateId(originPayload.getTemplate().getTemplateId());
            executePayload.setChannelType(detail.getChannelType().getType());

            // 简单起见，Execute 层可能只需要 ID，然后查库？或者尽可能带数据减少查库
            // 这里带上必要数据
            // TODO [优化] 接收人类型
            executePayload.setRecipient(MapUtil.of("email", detail.getTargetUser()));
            executePayload.setMsgVariables(detail.getMsgVariables());
            executePayload.setTenantId(detail.getTenantId());

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

        TaskSplitPayload payload = JSON.parseObject(message, TaskSplitPayload.class);
        if (payload == null || payload.getTaskId() == null) {
            log.error("[TaskSplitConsumer][忽略处理][消息体为空或TaskId缺失]");
            return null;
        }
        if (payload.getTenantId() == null) {
            log.error("[TaskSplitConsumer][忽略处理][租户ID缺失][业务ID: {}, 任务ID: {}, 载荷: {}]", payload.getBizId(), payload.getTaskId(), JSON.toJSONString(payload));
            return null;
        }

        return payload;
    }

}
