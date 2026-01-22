package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.domian.dto.NotifySendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * 站内信发送 API 接口
 */
public interface NotifyMessageSendApi {

    /**
     * 发送单条站内信给 Admin 用户
     *
     * @param reqDTO 发送请求
     * @return 发送消息 ID
     */
    Long sendSingleMessageToAdmin(@Valid NotifySendSingleToUserReqDTO reqDTO);


}
