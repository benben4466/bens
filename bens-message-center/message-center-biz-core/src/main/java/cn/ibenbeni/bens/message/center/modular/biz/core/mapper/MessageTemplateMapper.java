package cn.ibenbeni.bens.message.center.modular.biz.core.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplatePageReq;

public interface MessageTemplateMapper extends BaseMapperX<MessageTemplateDO> {

    default MessageTemplateDO selectByCode(String code) {
        return selectOne(MessageTemplateDO::getTemplateCode, code);
    }

    default PageResult<MessageTemplateDO> page(MessageTemplatePageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<MessageTemplateDO>()
                .likeIfPresent(MessageTemplateDO::getTemplateName, req.getTemplateName())
                .likeIfPresent(MessageTemplateDO::getTemplateCode, req.getTemplateCode())
                .eqIfPresent(MessageTemplateDO::getTemplateStatus, req.getTemplateStatus())
                .eqIfPresent(MessageTemplateDO::getAuditStatus, req.getAuditStatus())
                .eqIfPresent(MessageTemplateDO::getBizType, req.getBizType())
                .betweenIfPresent(MessageTemplateDO::getCreateTime, req.getCreateTime())
                .orderByDesc(MessageTemplateDO::getCreateTime)
        );
    }
}

