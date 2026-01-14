package cn.ibenbeni.bens.message.center.api.util;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.core.model.channelconfig.AbstractMessageChannelConfigSpecs;
import cn.ibenbeni.bens.message.center.api.core.model.channelconfig.EmailMessageChannelConfigSpecs;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;

import java.util.Map;
import java.util.Objects;

/**
 * 渠道配置工具类
 */
public class ChannelConfigUtils {

    /**
     * 将配置Map转换为具体的配置对象
     *
     * @param channelType 渠道类型
     * @param config      配置Map
     * @return 具体配置对象
     */
    public static AbstractMessageChannelConfigSpecs convert(Integer channelType, Map<String, Object> config) {
        if (channelType == null || config == null) {
            return null;
        }

        AbstractMessageChannelConfigSpecs specs = null;
        if (Objects.equals(channelType, MsgPushChannelTypeEnum.EMAIL.getType())) {
            specs = BeanUtil.toBean(config, EmailMessageChannelConfigSpecs.class);
        }

        if (specs != null) {
            specs.setChannelType(channelType);
        }

        return specs;
    }

}
