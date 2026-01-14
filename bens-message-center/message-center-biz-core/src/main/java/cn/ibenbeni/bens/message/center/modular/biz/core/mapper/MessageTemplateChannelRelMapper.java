package cn.ibenbeni.bens.message.center.modular.biz.core.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateChannelRelDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateChannelRelPageReq;

public interface MessageTemplateChannelRelMapper extends BaseMapperX<MessageTemplateChannelRelDO> {

    default PageResult<MessageTemplateChannelRelDO> page(MessageTemplateChannelRelPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<MessageTemplateChannelRelDO>()
                .eqIfPresent(MessageTemplateChannelRelDO::getTemplateContentId, req.getTemplateContentId())
                .eqIfPresent(MessageTemplateChannelRelDO::getChannelConfigId, req.getChannelConfigId())
                .orderByDesc(MessageTemplateChannelRelDO::getCreateTime)
        );
    }

}
