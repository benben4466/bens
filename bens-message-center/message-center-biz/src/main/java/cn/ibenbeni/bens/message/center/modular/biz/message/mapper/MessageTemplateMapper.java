package cn.ibenbeni.bens.message.center.modular.biz.message.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplatePageReq;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据模板ID、模板编码、渠道类型，查询数量。
     *
     * @param templateId   模板ID
     * @param templateCode 模板编码
     * @param channelType  渠道类型
     * @return 消息模板的渠道数量 or 消息模板数量；取决于 channelType
     */
    Long selectCountByTemplateAndChannel(@Param("templateId") Long templateId,
                                         @Param("templateCode") String templateCode,
                                         @Param("channelType") Integer channelType);
}

