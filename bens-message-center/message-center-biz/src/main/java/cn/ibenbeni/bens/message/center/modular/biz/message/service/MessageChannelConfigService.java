package cn.ibenbeni.bens.message.center.modular.biz.message.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageChannelConfigPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageChannelConfigSaveReq;

import java.util.List;
import java.util.Set;

public interface MessageChannelConfigService {

    Long create(MessageChannelConfigSaveReq req);

    void updateById(MessageChannelConfigSaveReq req);

    void deleteById(Long id);

    void deleteByIds(Set<Long> ids);

    MessageChannelConfigDO getById(Long id);

    PageResult<MessageChannelConfigDO> page(MessageChannelConfigPageReq req);

    /**
     * 根据渠道类型获取默认配置
     *
     * @param channelType 渠道类型
     * @return 渠道配置
     */
    MessageChannelConfigDO getByChannelType(Integer channelType);

    /**
     * 获取所有可用的渠道配置
     *
     * @param channelType 渠道类型
     * @return 渠道配置列表
     */
    List<MessageChannelConfigDO> listAvailableByChannelType(Integer channelType);

    /**
     * 根据配置ID列表获取渠道配置
     *
     * @param configIds 配置ID列表
     * @return 渠道配置列表
     */
    List<MessageChannelConfigDO> listByIds(Set<Long> configIds);

}
