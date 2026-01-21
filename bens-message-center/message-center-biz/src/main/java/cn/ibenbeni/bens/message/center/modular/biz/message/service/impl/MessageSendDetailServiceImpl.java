package cn.ibenbeni.bens.message.center.modular.biz.message.service.impl;

import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendDetailDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.mapper.MessageSendDetailMapper;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageSendDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息发送明细 Service 实现
 */
@Service
public class MessageSendDetailServiceImpl implements MessageSendDetailService {

    @Resource
    private MessageSendDetailMapper messageSendDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<MessageSendDetailDO> details) {
        if (details == null || details.isEmpty()) {
            return false;
        }
        
        // 简单的循环插入，实际生产建议优化为 Batch Insert
        for (MessageSendDetailDO detail : details) {
            messageSendDetailMapper.insert(detail);
        }
        return true;
    }

    @Override
    public boolean updateById(MessageSendDetailDO detail) {
        return messageSendDetailMapper.updateById(detail) > 0;
    }

    @Override
    public MessageSendDetailDO getById(Long id) {
        return messageSendDetailMapper.selectById(id);
    }

}
