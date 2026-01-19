package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageChannelConfigMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageChannelConfigPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageChannelConfigSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageChannelConfigService;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class MessageChannelConfigServiceImpl implements MessageChannelConfigService {

    @Resource
    private MessageChannelConfigMapper messageChannelConfigMapper;

    @Override
    public Long create(MessageChannelConfigSaveReq req) {
        validateCodeDuplicate(null, req.getChannelCode());
        MessageChannelConfigDO entity = BeanUtil.toBean(req, MessageChannelConfigDO.class);
        messageChannelConfigMapper.insert(entity);
        return entity.getConfigId();
    }

    @Override
    public void updateById(MessageChannelConfigSaveReq req) {
        validateExists(req.getConfigId());
        validateCodeDuplicate(req.getConfigId(), req.getChannelCode());
        MessageChannelConfigDO entity = BeanUtil.toBean(req, MessageChannelConfigDO.class);
        messageChannelConfigMapper.updateById(entity);
    }

    @Override
    public void deleteById(Long id) {
        validateExists(id);
        messageChannelConfigMapper.deleteById(id);
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        ids.forEach(this::validateExists);
        messageChannelConfigMapper.deleteBatchIds(ids);
    }

    @Override
    public MessageChannelConfigDO getById(Long id) {
        return messageChannelConfigMapper.selectById(id);
    }

    @Override
    public PageResult<MessageChannelConfigDO> page(MessageChannelConfigPageReq req) {
        return messageChannelConfigMapper.page(req);
    }

    @Override
    public MessageChannelConfigDO getByChannelType(Integer channelType) {
        return messageChannelConfigMapper.selectOne(new LambdaQueryWrapper<MessageChannelConfigDO>()
                .eq(MessageChannelConfigDO::getChannelType, channelType)
                .eq(MessageChannelConfigDO::getStatusFlag, StatusEnum.ENABLE)
                .last("LIMIT 1"));
    }

    @Override
    public List<MessageChannelConfigDO> listAvailableByChannelType(Integer channelType) {
        return messageChannelConfigMapper.selectList(new LambdaQueryWrapper<MessageChannelConfigDO>()
                .eq(MessageChannelConfigDO::getChannelType, channelType)
                .eq(MessageChannelConfigDO::getStatusFlag, StatusEnum.ENABLE));
    }

    @Override
    public List<MessageChannelConfigDO> listByIds(Set<Long> configIds) {
        if (CollUtil.isEmpty(configIds)) {
            return Collections.emptyList();
        }
        return messageChannelConfigMapper.selectBatchIds(configIds);
    }

    private void validateExists(Long id) {
        if (id == null || messageChannelConfigMapper.selectById(id) == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.CHANNEL_CONFIG_NOT_EXIST);
        }
    }

    private void validateCodeDuplicate(Long id, String code) {
        MessageChannelConfigDO exists = messageChannelConfigMapper.selectOne(new LambdaQueryWrapper<MessageChannelConfigDO>()
                .eq(MessageChannelConfigDO::getChannelCode, code));
        if (exists != null && !Objects.equals(exists.getConfigId(), id)) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CODE_DUPLICATE);
        }
    }
}
