package cn.ibenbeni.bens.message.center.modular.biz.core.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;

public interface MessageSendRecordMapper extends BaseMapperX<MessageSendRecordDO> {

    default PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<MessageSendRecordDO>()
                .likeIfPresent(MessageSendRecordDO::getTemplateCode, req.getTemplateCode())
                .eqIfPresent(MessageSendRecordDO::getChannelType, req.getChannelType())
                .eqIfPresent(MessageSendRecordDO::getSendStatus, req.getSendStatus())
                .betweenIfPresent(MessageSendRecordDO::getSendTime, req.getSendTime() != null ? req.getSendTime().toArray() : null)
                .orderByDesc(MessageSendRecordDO::getSendTime)
        );
    }
}
