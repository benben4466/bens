package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRecordCreateDTO;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRecordDTO;

/**
 * 消息发送记录管理 API 接口
 */
public interface MessageSendRecordApi {

    /**
     * 创建发送记录
     *
     * @param dto 创建请求
     * @return 记录ID
     */
    Long createRecord(MessageSendRecordCreateDTO dto);

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

    /**
     * 查询发送记录详情
     *
     * @param recordId 记录ID
     * @return 发送记录
     */
    MessageSendRecordDTO getById(Long recordId);

}
