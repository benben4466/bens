package cn.ibenbeni.bens.message.center.api.domian.message;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;

/**
 * 发送消息扩展信息
 */
public abstract class SendMessageExtraInfo {

    /**
     * 渠道类型
     * <p>枚举: {@link MsgPushChannelTypeEnum}</p>
     */
    private Integer channelType;

    /**
     * 渠道描述
     */
    private String channelDesc;

}
