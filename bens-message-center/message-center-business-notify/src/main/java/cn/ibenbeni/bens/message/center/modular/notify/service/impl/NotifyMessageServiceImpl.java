package cn.ibenbeni.bens.message.center.modular.notify.service.impl;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.notify.entity.NotifyMessageDO;
import cn.ibenbeni.bens.message.center.modular.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.notify.mapper.NotifyMessageMapper;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyMessageMyPageReq;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyMessagePageReq;
import cn.ibenbeni.bens.message.center.modular.notify.service.NotifyMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 站内信消息-服务实现
 */
@Slf4j
@Service
public class NotifyMessageServiceImpl extends ServiceImpl<NotifyMessageMapper, NotifyMessageDO> implements NotifyMessageService {

    @Resource
    private NotifyMessageMapper notifyMessageMapper;

    @Override
    public Long createNotifyMessage(Long userId, Integer userType, NotifyTemplateDO template, String templateContent, Map<String, Object> templateParams) {
        NotifyMessageDO notifyMessage = NotifyMessageDO.builder()
                .userId(userId)
                .userType(userType)
                .templateId(template.getId())
                .templateCode(template.getCode())
                .templateType(template.getType())
                .templateNickname(template.getNickname())
                .templateContent(templateContent)
                .templateParams(templateParams)
                .readStatus(false)
                .build();
        notifyMessageMapper.insert(notifyMessage);
        return notifyMessage.getId();
    }

    @Override
    public int updateNotifyMessageRead(Set<Long> messageIdSet, Long userId, Integer userType) {
        return notifyMessageMapper.updateListRead(messageIdSet, userId, userType);
    }

    @Override
    public int updateAllNotifyMessageRead(Long userId, Integer userType) {
        return notifyMessageMapper.updateListRead(userId, userType);
    }

    @Override
    public NotifyMessageDO getNotifyMessage(Long messageId) {
        return notifyMessageMapper.selectById(messageId);
    }

    @Override
    public List<NotifyMessageDO> listUnreadNotifyMessage(Long userId, Integer userType, Integer size) {
        return notifyMessageMapper.selectUnreadListByUserIdAndUserType(userId, userType, size);
    }

    @Override
    public PageResult<NotifyMessageDO> pageNotifyMessage(NotifyMessagePageReq pageReq) {
        return notifyMessageMapper.selectPage(pageReq);
    }

    @Override
    public PageResult<NotifyMessageDO> pageMyNotifyMessage(NotifyMessageMyPageReq pageReq, Long userId, Integer userType) {
        return notifyMessageMapper.selectPage(pageReq, userId, userType);
    }

    @Override
    public Long getUnreadNotifyMessageCount(Long userId, Integer userType) {
        return notifyMessageMapper.selectUnreadCountByUserIdAndUserType(userId, userType);
    }

}
