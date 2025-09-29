package cn.ibenbeni.bens.message.center.modular.notify.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyTemplatePageReq;

/**
 * 站内信-Mapper
 */
public interface NotifyTemplateMapper extends BaseMapperX<NotifyTemplateDO> {

    default NotifyTemplateDO selectByCode(String templateCode) {
        return selectOne(NotifyTemplateDO::getCode, templateCode);
    }

    default PageResult<NotifyTemplateDO> pageNotifyTemplate(NotifyTemplatePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<NotifyTemplateDO>()
                .likeIfPresent(NotifyTemplateDO::getName, pageReq.getName())
                .likeIfPresent(NotifyTemplateDO::getCode, pageReq.getCode())
                .eqIfPresent(NotifyTemplateDO::getStatusFlag, pageReq.getStatusFlag())
                .betweenIfPresent(NotifyTemplateDO::getCreateTime, pageReq.getCreateTime())
                .orderByDesc(NotifyTemplateDO::getId)
        );
    }

}
