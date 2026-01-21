package cn.ibenbeni.bens.message.center.modular.biz.notify.service.impl;

import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifyMessageService;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifySendService;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifyTemplateService;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.enums.user.UserTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 站内信发送-服务实现类
 */
@Slf4j
@Service
public class NotifySendServiceImpl implements NotifySendService {

    @Resource
    private NotifyTemplateService notifyTemplateService;

    @Resource
    private NotifyMessageService notifyMessageService;

    @Override
    public Long sendSingleNotifyToAdmin(Long userId, String templateCode, Map<String, Object> templateParams) {
        return sendSingleNotify(userId, UserTypeEnum.ADMIN.getType(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleNotify(Long userId, Integer userType, String templateCode, Map<String, Object> templateParams) {
        // 校验模板
        NotifyTemplateDO notifyTemplate = validateNotifyTemplate(templateCode);
        if (StatusEnum.DISABLE.getCode().equals(notifyTemplate.getStatusFlag())) {
            log.info("[站内信][sendSingleNotify][模板({})已关闭, 无法给用户({}/{})发送]", templateCode, userId, userType);
            return null;
        }

        // 校验参数
        validateTemplateParams(notifyTemplate, templateParams);
        // 发站内信
        String content = notifyTemplateService.formatNotifyTemplateContent(notifyTemplate.getContent(), templateParams);
        return notifyMessageService.createNotifyMessage(userId, userType, notifyTemplate, content, templateParams);
    }

    private NotifyTemplateDO validateNotifyTemplate(String templateCode) {
        NotifyTemplateDO notifyTemplate = notifyTemplateService.getNotifyTemplateByCode(templateCode);
        if (notifyTemplate == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.NOTIFY_TEMPLATE_NOT_EXIST);
        }
        return notifyTemplate;
    }

    /**
     * 校验模板参数
     *
     * @param template       模板
     * @param templateParams 参数
     */
    private void validateTemplateParams(NotifyTemplateDO template, Map<String, Object> templateParams) {
        template.getParams().forEach(param -> {
            Object value = templateParams.get(param);
            if (value == null) {
                throw new MessageCenterException(MessageCenterExceptionEnum.NOTIFY_SEND_TEMPLATE_PARAM_MISS, param);
            }
        });
    }

}
