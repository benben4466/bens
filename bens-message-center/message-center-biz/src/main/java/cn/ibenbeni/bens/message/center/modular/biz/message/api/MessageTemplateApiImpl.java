package cn.ibenbeni.bens.message.center.modular.biz.message.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgTemplateAuditStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgTemplateStatusEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateContentDTO;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateDTO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateContentService;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 消息模板查询 API 实现
 */
@Service
public class MessageTemplateApiImpl implements MessageTemplateApi {

    @Resource
    private MessageTemplateService messageTemplateService;

    @Resource
    private MessageTemplateContentService messageTemplateContentService;

    @Override
    public MessageTemplateDTO getByCode(String templateCode) {
        MessageTemplateDO entity = messageTemplateService.getByCode(templateCode);
        if (entity == null) {
            return null;
        }
        return convertToDTO(entity);
    }

    @Override
    public MessageTemplateDTO getById(Long templateId) {
        MessageTemplateDO entity = messageTemplateService.getById(templateId);
        if (entity == null) {
            return null;
        }
        return convertToDTO(entity);
    }

    @Override
    public MessageTemplateContentDTO getContentByTemplateCodeAndChannel(String templateCode, Integer channelType) {
        MessageTemplateContentDO entity = messageTemplateContentService.getByTemplateCodeAndChannelType(templateCode, channelType);
        if (entity == null) {
            return null;
        }
        return BeanUtil.toBean(entity, MessageTemplateContentDTO.class);
    }

    @Override
    public List<MessageTemplateContentDTO> listContentsByTemplateId(Long templateId) {
        List<MessageTemplateContentDO> list = messageTemplateContentService.listByTemplateId(templateId);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream()
                .map(item -> BeanUtil.toBean(item, MessageTemplateContentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageTemplateDTO checkTemplateAvailable(String templateCode) {
        MessageTemplateDO entity = messageTemplateService.getByCode(templateCode);
        if (entity == null) {
            return null;
        }
        // 检查模板状态是否已上线
        if (!MsgTemplateStatusEnum.ONLINE.getStatus().equals(entity.getTemplateStatus())) {
            return null;
        }
        // 检查审核状态是否通过
        if (!MsgTemplateAuditStatusEnum.APPROVED.getStatus().equals(entity.getAuditStatus())) {
            return null;
        }

        return convertToDTO(entity);
    }

    @Override
    public boolean isSupportChannel(String templateCode, Set<Integer> channelTypes) {
        return messageTemplateService.isSupportChannel(templateCode, channelTypes);
    }

    private MessageTemplateDTO convertToDTO(MessageTemplateDO entity) {
        MessageTemplateDTO dto = BeanUtil.toBean(entity, MessageTemplateDTO.class);
        // 转换内容列表
        if (entity.getContentList() != null && !entity.getContentList().isEmpty()) {
            List<MessageTemplateContentDTO> contentDTOList = entity.getContentList().stream()
                    .map(item -> BeanUtil.toBean(item, MessageTemplateContentDTO.class))
                    .collect(Collectors.toList());
            dto.setContentList(contentDTOList);
        }
        return dto;
    }
}
