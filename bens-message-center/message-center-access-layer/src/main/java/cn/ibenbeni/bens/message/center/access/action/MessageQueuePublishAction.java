package cn.ibenbeni.bens.message.center.access.action;

import cn.ibenbeni.bens.message.center.access.model.MessageSendContext;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageQueuePayload;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 消息队列投递 Action
 * 职责: 将已创建记录的消息投递到 RocketMQ 队列
 */
@Slf4j
@Component
public class MessageQueuePublishAction implements MessageSendAction {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${bens.message-center.send.topic:MESSAGE_CENTER_SEND_TOPIC}")
    private String topic;

    @Override
    public void execute(MessageSendContext context) {
        log.info("[MessageQueuePublishAction][开始投递消息到MQ][templateCode: {}]", context.getTemplateCode());

        List<Long> recordIds = context.getRecordIds();
        Map<Integer, MessageSendContext.ParsedContent> parsedContents = context.getParsedContents();

        // 确保记录已创建
        if (recordIds.isEmpty()) {
            log.error("[MessageQueuePublishAction][发送记录为空，无法投递]");
            context.interrupt("发送记录为空");
            return;
        }

        int index = 0;
        // 遍历每个渠道的解析内容，投递MQ
        for (Map.Entry<Integer, MessageSendContext.ParsedContent> entry : parsedContents.entrySet()) {
            Integer channelType = entry.getKey();
            MessageSendContext.ParsedContent parsedContent = entry.getValue();

            if (index >= recordIds.size()) {
                log.error("[MessageQueuePublishAction][记录ID与渠道数量不匹配]");
                break;
            }

            Long recordId = recordIds.get(index++);

            try {
                // 构建MQ消息体
                MessageQueuePayload payload = new MessageQueuePayload();
                payload.setRecordId(recordId);
                payload.setTemplateCode(context.getTemplateCode());
                payload.setTemplateId(context.getTemplate().getTemplateId());
                payload.setBizType(context.getBizType());
                payload.setBizId(context.getBizId());
                payload.setChannelType(channelType);
                payload.setRecipient(context.getRecipient());
                payload.setRecipientType(context.getRecipientType());
                payload.setMessageTitle(parsedContent.getTitle());
                payload.setMessageContent(parsedContent.getContent());
                payload.setMsgVariables(context.getTemplateParams());
                payload.setChannelConfig(parsedContent.getChannelConfig());
                payload.setRetryCount(0);
                payload.setSendTime(System.currentTimeMillis());
                payload.setTenantId(context.getTenantId());

                // 投递到MQ
                String destination = topic + ":" + channelType;
                rocketMQTemplate.convertAndSend(destination, JSON.toJSONString(payload));

                log.info("[MessageQueuePublishAction][消息投递成功][recordId: {}, channelType: {}, destination: {}]",
                        recordId, channelType, destination);

            } catch (Exception e) {
                log.error("[MessageQueuePublishAction][消息投递失败][recordId: {}, channelType: {}]",
                        recordId, channelType, e);
                throw new MessageCenterException(MessageCenterExceptionEnum.MESSAGE_QUEUE_SEND_FAIL);
            }
        }

        log.info("[MessageQueuePublishAction][消息投递完成][投递数量: {}]", index);
    }

    @Override
    public int getOrder() {
        return 400;
    }
}
