package cn.ibenbeni.bens.module.iot.core.messagebus.core.local;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IoT-本地消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotLocalMessage {

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息内容
     */
    private Object message;

}
