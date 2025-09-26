package cn.ibenbeni.bens.websocket.api.sender;

import com.alibaba.fastjson2.JSON;

/**
 * WebSocket 消息发送器接口
 */
public interface WebSocketMessageSender {

    /**
     * 发送消息给指定用户类型
     *
     * @param userType       用户类型
     * @param messageType    消息类型
     * @param messageContent 消息内容(JSON格式)
     */
    void send(Integer userType, String messageType, String messageContent);

    /**
     * 发送消息给指定用户
     *
     * @param userType       用户类型
     * @param userId         用户ID
     * @param messageType    消息类型
     * @param messageContent 消息内容(JSON格式)
     */
    void send(Integer userType, Long userId, String messageType, String messageContent);

    /**
     * 发送消息给指定 Session
     *
     * @param sessionId      SessionID
     * @param messageType    消息类型
     * @param messageContent 消息内容(JSON格式)
     */
    void send(String sessionId, String messageType, String messageContent);

    default void sendObject(Integer userType, String messageType, Object messageContent) {
        send(userType, messageType, JSON.toJSONString(messageContent));
    }

    default void sendObject(Integer userType, Long userId, String messageType, Object messageContent) {
        send(userType, userId, messageType, JSON.toJSONString(messageContent));
    }

    default void sendObject(String sessionId, String messageType, Object messageContent) {
        send(sessionId, messageType, JSON.toJSONString(messageContent));
    }

}
