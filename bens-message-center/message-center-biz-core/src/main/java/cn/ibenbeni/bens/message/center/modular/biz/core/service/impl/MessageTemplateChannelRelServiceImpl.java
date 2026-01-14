package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateChannelRelDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageTemplateChannelRelMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateChannelRelPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateChannelRelSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateChannelRelService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageTemplateChannelRelServiceImpl implements MessageTemplateChannelRelService {

    @Resource
    private MessageTemplateChannelRelMapper messageTemplateChannelRelMapper;

    @Override
    public Long create(MessageTemplateChannelRelSaveReq req) {
        MessageTemplateChannelRelDO entity = BeanUtil.toBean(req, MessageTemplateChannelRelDO.class);
        messageTemplateChannelRelMapper.insert(entity);
        return entity.getId();
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void createBatch(List<MessageTemplateChannelRelSaveReq> reqList) {
        if (reqList == null || reqList.isEmpty()) {
            return;
        }
        // 由于 MessageTemplateChannelRelMapper 继承自 BaseMapperX，可能不支持直接的 batchInsert
        // 这里采用循环插入，或者如果想优化，可以使用 MyBatis-Plus 的 IService.saveBatch
        // 鉴于目前没有继承 IService，且通常批量插入需求量不是特别巨大，循环插入是可接受的
        // 或者构建实体列表后循环插入
        for (MessageTemplateChannelRelSaveReq req : reqList) {
            MessageTemplateChannelRelDO entity = BeanUtil.toBean(req, MessageTemplateChannelRelDO.class);
            messageTemplateChannelRelMapper.insert(entity);
        }
    }

    @Override
    public void updateById(MessageTemplateChannelRelSaveReq req) {
        validateExists(req.getId());
        MessageTemplateChannelRelDO entity = BeanUtil.toBean(req, MessageTemplateChannelRelDO.class);
        messageTemplateChannelRelMapper.updateById(entity);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void updateRel(Long templateContentId, Set<Long> channelConfigIds) {
        // 1. 删除旧关联
        deleteByTemplateContentId(templateContentId);

        // 2. 新增新关联
        if (channelConfigIds != null && !channelConfigIds.isEmpty()) {
            List<MessageTemplateChannelRelSaveReq> reqList = new ArrayList<>();
            for (Long channelConfigId : channelConfigIds) {
                MessageTemplateChannelRelSaveReq req = new MessageTemplateChannelRelSaveReq();
                req.setTemplateContentId(templateContentId);
                req.setChannelConfigId(channelConfigId);
                reqList.add(req);
            }
            createBatch(reqList);
        }
    }

    @Override
    public void deleteById(Long id) {
        validateExists(id);
        messageTemplateChannelRelMapper.deleteById(id);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(this::validateExists);
        messageTemplateChannelRelMapper.deleteBatchIds(ids);
    }

    @Override
    public void deleteByTemplateContentId(Long templateContentId) {
        if (templateContentId == null) {
            return;
        }
        messageTemplateChannelRelMapper.delete(new LambdaQueryWrapper<MessageTemplateChannelRelDO>()
                .eq(MessageTemplateChannelRelDO::getTemplateContentId, templateContentId));
    }

    @Override
    public void deleteByTemplateContentIds(Set<Long> templateContentIds) {
        if (templateContentIds == null || templateContentIds.isEmpty()) {
            return;
        }
        messageTemplateChannelRelMapper.delete(new LambdaQueryWrapper<MessageTemplateChannelRelDO>()
                .in(MessageTemplateChannelRelDO::getTemplateContentId, templateContentIds));
    }

    @Override
    public MessageTemplateChannelRelDO getById(Long id) {
        return messageTemplateChannelRelMapper.selectById(id);
    }

    @Override
    public PageResult<MessageTemplateChannelRelDO> page(MessageTemplateChannelRelPageReq req) {
        return messageTemplateChannelRelMapper.page(req);
    }

    private void validateExists(Long id) {
        if (id == null || messageTemplateChannelRelMapper.selectById(id) == null) {
            // 这里可能需要新增一个特定的异常枚举，暂时使用通用异常或者复用
            // 假设 MessageCenterExceptionEnum 中有相关的配置，暂时使用 RUNTIME_EXCEPTION 或者新增
            // 这里为了稳妥，抛出一个通用的异常，提示记录不存在
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CONTENT_NOT_EXIST); 
        }
    }
}
