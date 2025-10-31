package cn.ibenbeni.bens.module.iot.gateway.codec.alink;

import cn.hutool.core.lang.Assert;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.gateway.codec.IotDeviceMessageCodec;
import cn.ibenbeni.bens.rule.util.JsonUtils;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 阿里云 Alink 数据格式 {@link IotDeviceMessage} 编解码器
 */
@Component
public class IotAlinkDeviceMessageCodec implements IotDeviceMessageCodec {

    private static final String TYPE = "Alink";

    @Override
    public byte[] encode(IotDeviceMessage message) {
        AlinkMessage alinkMessage = AlinkMessage.builder()
                .id(message.getMsgId())
                .version(AlinkMessage.VERSION_1)
                .method(message.getMethod())
                .params(message.getParams())
                .data(message.getData())
                .code(message.getCode())
                .message(message.getMsg())
                .build();

        return JsonUtils.toJsonStr(alinkMessage).getBytes();
    }

    @Override
    public IotDeviceMessage decode(byte[] bytes) {
        AlinkMessage alinkMessage = JSON.parseObject(bytes, AlinkMessage.class);
        Assert.notNull(alinkMessage, "消息不能为空");
        Assert.equals(alinkMessage.getVersion(), AlinkMessage.VERSION_1, "消息版本号必须是 1.0");
        return IotDeviceMessage.builder()
                .msgId(alinkMessage.getId())
                .method(alinkMessage.getMethod())
                .params(alinkMessage.getParams())
                .data(alinkMessage.getData())
                .code(alinkMessage.getCode())
                .msg(alinkMessage.getMessage())
                .build();
    }

    @Override
    public String type() {
        return TYPE;
    }

    /**
     * 阿里云 Alink 数据格式
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AlinkMessage {

        public static final String VERSION_1 = "1.0";

        /**
         * 消息 ID，且每个消息 ID 在当前设备具有唯一性
         */
        private String id;

        /**
         * 版本号
         */
        private String version;

        /**
         * 请求方法
         */
        private String method;

        /**
         * 请求参数
         */
        private Object params;

        /**
         * 响应结果
         */
        private Object data;

        /**
         * 响应错误码
         * <p>特殊：阿里是int类型</p>
         */
        private String code;

        /**
         * 响应提示
         */
        private String message;

    }

}
