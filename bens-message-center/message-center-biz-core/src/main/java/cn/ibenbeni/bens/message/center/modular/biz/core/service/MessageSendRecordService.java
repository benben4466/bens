package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordCreateReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;

/**
 * 消息发送记录-Service层
 */
public interface MessageSendRecordService {

    Long create(MessageSendRecordCreateReq req);

    MessageSendRecordDO getById(Long id);

    PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req);

    /**
     * 更新记录状态
     *
     * @param recordId     记录ID
     * @param status       发送状态
     * @param responseData 响应数据
     */
    void updateRecordStatus(Long recordId, MsgSendStatusEnum status, String responseData);

    /**
     * 更新记录为失败状态
     *
     * @param recordId   记录ID
     * @param failType   失败类型
     * @param failReason 失败原因
     */
    void updateRecordFailed(Long recordId, MsgSendFailTypeEnum failType, String failReason);

    /**
     * 增加重试次数
     *
     * @param recordId 记录ID
     */
    void increaseRetryCount(Long recordId);

}
