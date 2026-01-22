package cn.ibenbeni.bens.message.center.modular.biz.notify.api;

import cn.ibenbeni.bens.message.center.api.NotifyMessageSendApi;
import cn.ibenbeni.bens.message.center.api.domian.dto.NotifySendSingleToUserReqDTO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifySendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 站内信发送 API 实现类
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public Long sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
