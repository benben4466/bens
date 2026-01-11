package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateSaveReq;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageTemplateMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateContentService;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateContentDO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Resource
    private MessageTemplateMapper messageTemplateMapper;

    @Resource
    private MessageTemplateContentService messageTemplateContentService;

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public Long create(MessageTemplateSaveReq req) {
        validateCodeDuplicate(null, req.getTemplateCode());
        MessageTemplateDO entity = BeanUtil.toBean(req, MessageTemplateDO.class);
        messageTemplateMapper.insert(entity);

        // 创建模板内容
        if (req.getContentList() != null && !req.getContentList().isEmpty()) {
            req.getContentList().forEach(contentReq -> contentReq.setTemplateId(entity.getTemplateId()));
            messageTemplateContentService.createBatch(req.getContentList());
        }

        return entity.getTemplateId();
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateById(MessageTemplateSaveReq req) {
        validateExists(req.getTemplateId());
        validateCodeDuplicate(req.getTemplateId(), req.getTemplateCode());
        MessageTemplateDO entity = BeanUtil.toBean(req, MessageTemplateDO.class);
        messageTemplateMapper.updateById(entity);

        // 更新模板内容（先删后增）
        if (req.getContentList() != null) {
            messageTemplateContentService.deleteByTemplateId(req.getTemplateId());
            if (!req.getContentList().isEmpty()) {
                req.getContentList().forEach(contentReq -> contentReq.setTemplateId(entity.getTemplateId()));
                messageTemplateContentService.createBatch(req.getContentList());
            }
        }
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        validateExists(id);
        messageTemplateContentService.deleteByTemplateId(id);
        messageTemplateMapper.deleteById(id);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(this::validateExists);
        messageTemplateContentService.deleteByTemplateIds(ids);
        messageTemplateMapper.deleteBatchIds(ids);
    }

    @Override
    public MessageTemplateDO getById(Long id) {
        MessageTemplateDO entity = messageTemplateMapper.selectById(id);
        if (entity != null) {
            entity.setContentList(messageTemplateContentService.listByTemplateId(id));
        }
        return entity;
    }

    @Override
    public MessageTemplateDO getByCode(String templateCode) {
        return messageTemplateMapper.selectByCode(templateCode);
    }

    @Override
    public PageResult<MessageTemplateDO> page(MessageTemplatePageReq req) {
        PageResult<MessageTemplateDO> page = messageTemplateMapper.page(req);
        if (page.getRows() != null && !page.getRows().isEmpty()) {
            Set<Long> templateIds = page.getRows().stream()
                    .map(MessageTemplateDO::getTemplateId)
                    .collect(Collectors.toSet());

            if (!templateIds.isEmpty()) {
                List<MessageTemplateContentDO> contentList = messageTemplateContentService.listByTemplateIds(templateIds);
                Map<Long, List<MessageTemplateContentDO>> contentMap = contentList.stream()
                        .collect(Collectors.groupingBy(MessageTemplateContentDO::getTemplateId));

                page.getRows().forEach(row -> row.setContentList(contentMap.get(row.getTemplateId())));
            }
        }
        return page;
    }

    @Override
    public boolean checkExists(Long templateId, String templateCode, Integer channelType) {
        Long count = messageTemplateMapper.selectCountByTemplateAndChannel(templateId, templateCode, channelType);
        return count != null && count > 0;
    }

    private void validateExists(Long id) {
        if (id == null || messageTemplateMapper.selectById(id) == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }
    }

    private void validateCodeDuplicate(Long id, String code) {
        MessageTemplateDO exists = messageTemplateMapper.selectByCode(code);
        if (exists != null && !Objects.equals(exists.getTemplateId(), id)) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CODE_DUPLICATE);
        }
    }
}
