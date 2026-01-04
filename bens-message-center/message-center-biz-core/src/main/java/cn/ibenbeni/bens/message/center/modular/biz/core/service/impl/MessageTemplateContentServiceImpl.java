package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageTemplateContentMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class MessageTemplateContentServiceImpl implements MessageTemplateContentService {

    @Resource
    private MessageTemplateContentMapper messageTemplateContentMapper;

    @Resource
    private MessageTemplateService messageTemplateService;

    @Override
    public Long create(MessageTemplateContentSaveReq req) {
        validateTemplateExists(req.getTemplateId());
        validateUnique(req.getTemplateId(), req.getChannelType(), null);
        MessageTemplateContentDO entity = BeanUtil.toBean(req, MessageTemplateContentDO.class);
        messageTemplateContentMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateById(MessageTemplateContentSaveReq req) {
        validateExists(req.getId());
        validateTemplateExists(req.getTemplateId());
        validateUnique(req.getTemplateId(), req.getChannelType(), req.getId());
        MessageTemplateContentDO entity = BeanUtil.toBean(req, MessageTemplateContentDO.class);
        messageTemplateContentMapper.updateById(entity);
    }

    @Override
    public void deleteById(Long id) {
        validateExists(id);
        messageTemplateContentMapper.deleteById(id);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(this::validateExists);
        messageTemplateContentMapper.deleteBatchIds(ids);
    }

    @Override
    public MessageTemplateContentDO getById(Long id) {
        return messageTemplateContentMapper.selectById(id);
    }

    @Override
    public List<MessageTemplateContentDO> listByTemplateId(Long templateId) {
        return messageTemplateContentMapper.listByTemplateId(templateId);
    }

    @Override
    public PageResult<MessageTemplateContentDO> page(MessageTemplateContentPageReq req) {
        return messageTemplateContentMapper.page(req);
    }

    private void validateTemplateExists(Long templateId) {
        if (templateId == null || messageTemplateService.getById(templateId) == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }
    }

    private void validateExists(Long id) {
        if (id == null || messageTemplateContentMapper.selectById(id) == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CONTENT_NOT_EXIST);
        }
    }

    private void validateUnique(Long templateId, Integer channelType, Long id) {
        MessageTemplateContentDO exists = messageTemplateContentMapper.selectByTemplateIdAndChannelType(templateId, channelType);
        if (exists != null && !Objects.equals(exists.getId(), id)) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CONTENT_DUPLICATE);
        }
    }
}
