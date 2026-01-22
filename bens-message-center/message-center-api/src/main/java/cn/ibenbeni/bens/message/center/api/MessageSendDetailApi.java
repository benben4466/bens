package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendDetailDTO;

import java.util.List;

/**
 * 消息发送明细 API
 */
public interface MessageSendDetailApi {

    /**
     * 批量保存明细
     *
     * @param detailDTOList 明细列表
     * @return 是否成功
     */
    boolean saveBatch(List<MessageSendDetailDTO> detailDTOList);

    /**
     * 更新明细
     *
     * @param detailDTO 明细信息
     * @return 是否成功
     */
    boolean updateDetail(MessageSendDetailDTO detailDTO);

    /**
     * 获取明细详情
     * @param id 明细ID
     * @return 明细信息
     */
    MessageSendDetailDTO getDetailById(Long id);

}
