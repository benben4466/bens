package cn.ibenbeni.bens.message.center.modular.biz.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.mapper.MessageTemplateContentMapper;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateContentPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateContentSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateChannelRelSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateChannelRelService;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateContentService;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageTemplateContentServiceImpl implements MessageTemplateContentService {

    @Resource
    private MessageTemplateContentMapper messageTemplateContentMapper;

    @Lazy
    @Resource
    private MessageTemplateService messageTemplateService;

    @Resource
    private MessageTemplateChannelRelService messageTemplateChannelRelService;

    @Override
    public Long create(MessageTemplateContentSaveReq req) {
        validateTemplateExists(req.getTemplateId());
        validateUnique(req.getTemplateId(), req.getChannelType(), null);
        MessageTemplateContentDO entity = BeanUtil.toBean(req, MessageTemplateContentDO.class);
        messageTemplateContentMapper.insert(entity);
        
        // 保存渠道配置关联关系
        if (CollUtil.isNotEmpty(req.getChannelConfigIds())) {
            List<MessageTemplateChannelRelSaveReq> relReqList = new ArrayList<>();
            req.getChannelConfigIds().forEach(channelConfigId -> {
                MessageTemplateChannelRelSaveReq relReq = new MessageTemplateChannelRelSaveReq();
                relReq.setTemplateContentId(entity.getId());
                relReq.setChannelConfigId(channelConfigId);
                relReqList.add(relReq);
            });
            messageTemplateChannelRelService.createBatch(relReqList);
        }
        
        return entity.getId();
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void createBatch(List<MessageTemplateContentSaveReq> reqList) {
        if (reqList == null || reqList.isEmpty()) {
            return;
        }
        reqList.forEach(req -> {
            validateTemplateExists(req.getTemplateId());
            validateUnique(req.getTemplateId(), req.getChannelType(), null);
            MessageTemplateContentDO entity = BeanUtil.toBean(req, MessageTemplateContentDO.class);
            messageTemplateContentMapper.insert(entity);

            // 保存渠道配置关联关系
            if (CollUtil.isNotEmpty(req.getChannelConfigIds())) {
                List<MessageTemplateChannelRelSaveReq> relReqList = new ArrayList<>();
                req.getChannelConfigIds().forEach(channelConfigId -> {
                    MessageTemplateChannelRelSaveReq relReq = new MessageTemplateChannelRelSaveReq();
                    relReq.setTemplateContentId(entity.getId());
                    relReq.setChannelConfigId(channelConfigId);
                    relReqList.add(relReq);
                });
                messageTemplateChannelRelService.createBatch(relReqList);
            }
        });
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateById(MessageTemplateContentSaveReq req) {
        validateExists(req.getId());
        validateTemplateExists(req.getTemplateId());
        validateUnique(req.getTemplateId(), req.getChannelType(), req.getId());
        MessageTemplateContentDO entity = BeanUtil.toBean(req, MessageTemplateContentDO.class);
        messageTemplateContentMapper.updateById(entity);

        // 更新渠道配置关联关系
        messageTemplateChannelRelService.updateRel(entity.getId(), req.getChannelConfigIds());
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        validateExists(id);
        messageTemplateChannelRelService.deleteByTemplateContentId(id);
        messageTemplateContentMapper.deleteById(id);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(this::validateExists);
        messageTemplateChannelRelService.deleteByTemplateContentIds(ids);
        messageTemplateContentMapper.deleteByIds(ids);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByTemplateId(Long templateId) {
        if (templateId == null) {
            return;
        }
        // 先查出所有内容ID
        List<MessageTemplateContentDO> contentList = messageTemplateContentMapper.listByTemplateId(templateId);
        if (CollUtil.isNotEmpty(contentList)) {
            Set<Long> contentIds = contentList.stream().map(MessageTemplateContentDO::getId).collect(Collectors.toSet());
            messageTemplateChannelRelService.deleteByTemplateContentIds(contentIds);
        }
        messageTemplateContentMapper.delete(new LambdaQueryWrapper<MessageTemplateContentDO>()
                .eq(MessageTemplateContentDO::getTemplateId, templateId));
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByTemplateIds(Set<Long> templateIds) {
        if (templateIds == null || templateIds.isEmpty()) {
            return;
        }
        // 先查出所有内容ID
        List<MessageTemplateContentDO> contentList = messageTemplateContentMapper.listByTemplateIds(templateIds);
        if (CollUtil.isNotEmpty(contentList)) {
            Set<Long> contentIds = contentList.stream().map(MessageTemplateContentDO::getId).collect(Collectors.toSet());
            messageTemplateChannelRelService.deleteByTemplateContentIds(contentIds);
        }
        messageTemplateContentMapper.delete(new LambdaQueryWrapper<MessageTemplateContentDO>()
                .in(MessageTemplateContentDO::getTemplateId, templateIds));
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
    public List<MessageTemplateContentDO> listByTemplateIds(Set<Long> templateIds) {
        return messageTemplateContentMapper.listByTemplateIds(templateIds);
    }

    @Override
    public PageResult<MessageTemplateContentDO> page(MessageTemplateContentPageReq req) {
        return messageTemplateContentMapper.page(req);
    }

    @Override
    public MessageTemplateContentDO getByTemplateIdAndChannelType(Long templateId, Integer channelType) {
        return messageTemplateContentMapper.selectByTemplateIdAndChannelType(templateId, channelType);
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
