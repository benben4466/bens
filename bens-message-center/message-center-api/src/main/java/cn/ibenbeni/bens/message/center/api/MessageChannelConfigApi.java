package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageChannelConfigDTO;

import java.util.List;

/**
 * 消息渠道配置查询 API 接口
 */
public interface MessageChannelConfigApi {

    /**
     * 根据渠道类型获取默认配置
     *
     * @param channelType 渠道类型
     * @return 渠道配置
     */
    MessageChannelConfigDTO getByChannelType(Integer channelType);

    /**
     * 根据配置ID获取渠道配置
     *
     * @param configId 配置ID
     * @return 渠道配置
     */
    MessageChannelConfigDTO getById(Long configId);

    /**
     * 获取模板内容关联的渠道配置列表
     *
     * @param templateContentId 模板内容ID
     * @return 渠道配置列表
     */
    List<MessageChannelConfigDTO> listByTemplateContentId(Long templateContentId);

    /**
     * 获取所有可用的渠道配置
     *
     * @param channelType 渠道类型
     * @return 渠道配置列表
     */
    List<MessageChannelConfigDTO> listAvailableByChannelType(Integer channelType);

}
