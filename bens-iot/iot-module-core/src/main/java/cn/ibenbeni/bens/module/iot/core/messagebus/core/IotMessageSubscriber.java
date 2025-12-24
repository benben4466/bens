package cn.ibenbeni.bens.module.iot.core.messagebus.core;

/**
 * IoT-消息总线订阅者-接口
 * <p>用于处理从消息总线接收到的消息</p>
 *
 * @param <T> 消息类型
 */
public interface IotMessageSubscriber<T> {

    /**
     * @return 订阅主题
     */
    String getTopic();

    /**
     * @return 订阅分组
     */
    String getGroup();

    /**
     * 处理接收到的消息
     *
     * @param message 消息内容
     */
    void onMessage(T message);

}
