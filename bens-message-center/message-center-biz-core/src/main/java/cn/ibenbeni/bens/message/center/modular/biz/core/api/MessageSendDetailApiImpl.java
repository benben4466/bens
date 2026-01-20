package cn.ibenbeni.bens.message.center.modular.biz.core.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageSendDetailApi;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendDetailDTO;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendDetailDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageSendDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息发送明细 API 实现
 */
@Service
public class MessageSendDetailApiImpl implements MessageSendDetailApi {

    @Resource
    private MessageSendDetailService messageSendDetailService;

    @Override
    public boolean saveBatch(List<MessageSendDetailDTO> detailDTOList) {
        if (detailDTOList == null || detailDTOList.isEmpty()) {
            return false;
        }
        List<MessageSendDetailDO> detailDOList = detailDTOList.stream()
                .map(dto -> BeanUtil.copyProperties(dto, MessageSendDetailDO.class))
                .collect(Collectors.toList());
        
        boolean success = messageSendDetailService.saveBatch(detailDOList);
        
        // 回填 ID (虽然批量插入未必能回填所有 ID，取决于具体实现，这里尽力而为)
        if (success) {
            for (int i = 0; i < detailDOList.size(); i++) {
                detailDTOList.get(i).setId(detailDOList.get(i).getId());
            }
        }
        return success;
    }

    @Override
    public boolean updateDetail(MessageSendDetailDTO detailDTO) {
        MessageSendDetailDO detailDO = BeanUtil.copyProperties(detailDTO, MessageSendDetailDO.class);
        return messageSendDetailService.updateById(detailDO);
    }

    @Override
    public MessageSendDetailDTO getDetailById(Long id) {
        MessageSendDetailDO detailDO = messageSendDetailService.getById(id);
        if (detailDO == null) {
            return null;
        }
        return BeanUtil.copyProperties(detailDO, MessageSendDetailDTO.class);
    }
}
