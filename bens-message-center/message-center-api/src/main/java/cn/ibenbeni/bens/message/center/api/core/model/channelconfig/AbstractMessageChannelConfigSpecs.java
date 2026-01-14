package cn.ibenbeni.bens.message.center.api.core.model.channelconfig;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import lombok.Data;

/**
 * 消息渠道配置定义类-抽象类
 */
@Data
public abstract class AbstractMessageChannelConfigSpecs {

    /**
     * 渠道类型
     * <p>枚举：{@link MsgPushChannelTypeEnum}</p>
     */
    private Integer channelType;

}
