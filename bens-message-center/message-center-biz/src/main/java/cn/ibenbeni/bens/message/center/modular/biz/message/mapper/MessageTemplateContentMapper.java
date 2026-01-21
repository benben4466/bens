package cn.ibenbeni.bens.message.center.modular.biz.message.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateContentPageReq;

import java.util.List;
import java.util.Set;

public interface MessageTemplateContentMapper extends BaseMapperX<MessageTemplateContentDO> {

    default MessageTemplateContentDO selectByTemplateIdAndChannelType(Long templateId, Integer channelType) {
        return selectOne(new LambdaQueryWrapperX<MessageTemplateContentDO>()
                .eq(MessageTemplateContentDO::getTemplateId, templateId)
                .eq(MessageTemplateContentDO::getChannelType, channelType)
        );
    }

    default List<MessageTemplateContentDO> listByTemplateId(Long templateId) {
        return selectList(new LambdaQueryWrapperX<MessageTemplateContentDO>()
                .eq(MessageTemplateContentDO::getTemplateId, templateId)
                .orderByDesc(MessageTemplateContentDO::getCreateTime)
        );
    }

    default List<MessageTemplateContentDO> listByTemplateIds(Set<Long> templateIds) {
        return selectList(new LambdaQueryWrapperX<MessageTemplateContentDO>()
                .inIfPresent(MessageTemplateContentDO::getTemplateId, templateIds)
                .orderByDesc(MessageTemplateContentDO::getCreateTime)
        );
    }

    default PageResult<MessageTemplateContentDO> page(MessageTemplateContentPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<MessageTemplateContentDO>()
                .eqIfPresent(MessageTemplateContentDO::getTemplateId, req.getTemplateId())
                .eqIfPresent(MessageTemplateContentDO::getChannelType, req.getChannelType())
                .likeIfPresent(MessageTemplateContentDO::getTitle, req.getTitle())
                .orderByDesc(MessageTemplateContentDO::getCreateTime)
        );
    }
}

