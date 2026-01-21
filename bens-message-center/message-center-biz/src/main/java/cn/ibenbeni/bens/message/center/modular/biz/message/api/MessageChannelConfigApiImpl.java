package cn.ibenbeni.bens.message.center.modular.biz.message.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageChannelConfigApi;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageChannelConfigDTO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateChannelRelDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageChannelConfigService;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateChannelRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 消息渠道配置查询 API 实现
 */
@Service
public class MessageChannelConfigApiImpl implements MessageChannelConfigApi {

    @Resource
    private MessageChannelConfigService messageChannelConfigService;

    @Resource
    private MessageTemplateChannelRelService messageTemplateChannelRelService;

    @Override
    public MessageChannelConfigDTO getByChannelType(Integer channelType) {
        MessageChannelConfigDO entity = messageChannelConfigService.getByChannelType(channelType);
        if (entity == null) {
            return null;
        }
        return convertToDTO(entity);
    }

    @Override
    public MessageChannelConfigDTO getById(Long configId) {
        MessageChannelConfigDO entity = messageChannelConfigService.getById(configId);
        if (entity == null) {
            return null;
        }
        return convertToDTO(entity);
    }

    @Override
    public List<MessageChannelConfigDTO> listByTemplateContentId(Long templateContentId) {
        // 查询模板内容关联的渠道配置ID
        List<MessageTemplateChannelRelDO> relList = messageTemplateChannelRelService.listByTemplateContentId(templateContentId);
        if (relList == null || relList.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> configIds = relList.stream()
                .map(MessageTemplateChannelRelDO::getChannelConfigId)
                .collect(Collectors.toSet());
        
        // 查询渠道配置
        List<MessageChannelConfigDO> configList = messageChannelConfigService.listByIds(configIds);
        if (configList == null || configList.isEmpty()) {
            return Collections.emptyList();
        }
        return configList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageChannelConfigDTO> listAvailableByChannelType(Integer channelType) {
        List<MessageChannelConfigDO> list = messageChannelConfigService.listAvailableByChannelType(channelType);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MessageChannelConfigDTO convertToDTO(MessageChannelConfigDO entity) {
        MessageChannelConfigDTO dto = BeanUtil.toBean(entity, MessageChannelConfigDTO.class);
        if (entity.getStatusFlag() != null) {
            dto.setStatusFlag(entity.getStatusFlag().getCode());
        }
        return dto;
    }

}
