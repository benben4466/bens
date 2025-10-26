package cn.ibenbeni.bens.module.iot.core.messagebus.core;

/**
 * IoT-消息总线-接口
 * <p>用于在IOT系统中，发布和订阅消息，支持多种消息中间件</p>
 */
public interface IotMessageBus {

    /**
     * 发布消息到消息总线
     *
     * @param topic   主题
     * @param message 消息内容
     */
    void post(String topic, Object message);

    /**
     * 注册消息订阅者
     *
     * @param subscriber 订阅者
     */
    void register(IotMessageSubscriber<?> subscriber);

}
