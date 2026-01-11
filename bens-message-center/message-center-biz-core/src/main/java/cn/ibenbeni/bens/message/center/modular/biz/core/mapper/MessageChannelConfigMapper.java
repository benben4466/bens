package cn.ibenbeni.bens.message.center.modular.biz.core.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageChannelConfigPageReq;

public interface MessageChannelConfigMapper extends BaseMapperX<MessageChannelConfigDO> {

    default PageResult<MessageChannelConfigDO> page(MessageChannelConfigPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<MessageChannelConfigDO>()
                .likeIfPresent(MessageChannelConfigDO::getChannelCode, req.getChannelCode())
                .likeIfPresent(MessageChannelConfigDO::getChannelName, req.getChannelName())
                .eqIfPresent(MessageChannelConfigDO::getChannelType, req.getChannelType())
                .eqIfPresent(MessageChannelConfigDO::getStatusFlag, req.getStatusFlag())
                .orderByDesc(MessageChannelConfigDO::getCreateTime)
        );
    }
}
