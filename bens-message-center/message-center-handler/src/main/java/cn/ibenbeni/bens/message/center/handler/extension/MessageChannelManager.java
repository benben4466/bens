package cn.ibenbeni.bens.message.center.handler.extension;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息渠道管理器
 * 自动发现和管理所有消息渠道扩展
 * 
 * @author bens
 */
@Slf4j
@Component
public class MessageChannelManager {

    @Resource
    private List<MessageChannelExtension> channelExtensions;

    private final Map<MsgPushChannelTypeEnum, MessageChannelExtension> channelMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        if (channelExtensions != null && !channelExtensions.isEmpty()) {
            this.channelMap.putAll(
                    channelExtensions.stream()
                            .collect(Collectors.toMap(
                                    MessageChannelExtension::getSupportedChannel,
                                    Function.identity(),
                                    (existing, replacement) -> {
                                        log.warn("[MessageChannelManager][渠道冲突，保留第一个][channel: {}]", existing.getSupportedChannel());
                                        return existing;
                                    }
                            ))
            );

            log.info("[MessageChannelManager][初始化完成][已注册渠道: {}]",
                    channelMap.keySet().stream()
                            .map(channel -> channel.getDesc() + "(" + channel.getType() + ")")
                            .collect(Collectors.joining(", ")));
        } else {
            log.warn("[MessageChannelManager][未发现任何消息渠道扩展]");
        }
    }

    /**
     * 获取指定渠道的扩展实现
     * 
     * @param channelType 渠道类型
     * @return 渠道扩展实现，未找到返回null
     */
    public MessageChannelExtension getChannel(MsgPushChannelTypeEnum channelType) {
        return channelMap.get(channelType);
    }

    /**
     * 检查渠道是否已注册
     * 
     * @param channelType 渠道类型
     * @return true=已注册, false=未注册
     */
    public boolean isChannelRegistered(MsgPushChannelTypeEnum channelType) {
        return channelMap.containsKey(channelType);
    }

    /**
     * 检查渠道是否可用
     * 
     * @param channelType 渠道类型
     * @return true=可用, false=不可用
     */
    public boolean isChannelAvailable(MsgPushChannelTypeEnum channelType) {
        MessageChannelExtension extension = channelMap.get(channelType);
        return extension != null && extension.isAvailable();
    }

    /**
     * 获取所有已注册的渠道列表
     * 
     * @return 渠道类型列表
     */
    public List<MsgPushChannelTypeEnum> getRegisteredChannels() {
        return new ArrayList<>(channelMap.keySet());
    }
}
