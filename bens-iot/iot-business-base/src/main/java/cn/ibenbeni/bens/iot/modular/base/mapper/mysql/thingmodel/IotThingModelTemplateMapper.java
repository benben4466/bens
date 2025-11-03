package cn.ibenbeni.bens.iot.modular.base.mapper.mysql.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelTemplateDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplatePageReq;

/**
 * IOT-物模型模板-Mapper
 */
public interface IotThingModelTemplateMapper extends BaseMapperX<IotThingModelTemplateDO> {

    default IotThingModelTemplateDO selectByIdentifier(String identifier) {
        return selectOne(IotThingModelTemplateDO::getIdentifier, identifier);
    }

    default IotThingModelTemplateDO selectByName(String name) {
        return selectOne(IotThingModelTemplateDO::getName, name);
    }

    default PageResult<IotThingModelTemplateDO> selectPage(IotThingModelTemplatePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<IotThingModelTemplateDO>()
                .eqIfPresent(IotThingModelTemplateDO::getType, pageReq.getType())
                .likeIfPresent(IotThingModelTemplateDO::getName, pageReq.getName())
        );
    }

}
