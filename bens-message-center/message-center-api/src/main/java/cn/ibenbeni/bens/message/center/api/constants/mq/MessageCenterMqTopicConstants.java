package cn.ibenbeni.bens.message.center.api.constants.mq;

/**
 * 消息中心-MQ 主题常量类
 */
public interface MessageCenterMqTopicConstants {

    /**
     * 消息拆分 Topic
     */
    String SPLIT_TOPIC = "MESSAGE_CENTER_SPLIT_TOPIC";

    /**
     * 消息拆分 消费者组
     */
    String SPLIT_CONSUMER_GROUP = "MESSAGE_CENTER_SPLIT_GROUP";

    /**
     * 消息执行 Topic
     */
    String EXECUTE_TOPIC = "MESSAGE_CENTER_EXECUTE_TOPIC";

    /**
     * 消息执行 消费者组
     */
    String EXECUTE_CONSUMER_GROUP = "MESSAGE_CENTER_EXECUTE_CONSUMER_GROUP";

}
