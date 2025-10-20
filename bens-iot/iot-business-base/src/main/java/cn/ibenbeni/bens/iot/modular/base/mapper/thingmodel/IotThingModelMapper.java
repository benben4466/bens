package cn.ibenbeni.bens.iot.modular.base.mapper.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelPageReq;

import java.util.List;

/**
 * IOT-物模型-Mapper
 */
public interface IotThingModelMapper extends BaseMapperX<IotThingModelDO> {

    default IotThingModelDO selectByIdentifier(String identifier) {
        return selectOne(IotThingModelDO::getIdentifier, identifier);
    }

    default IotThingModelDO selectByName(String name) {
        return selectOne(IotThingModelDO::getName, name);
    }

    default List<IotThingModelDO> selectListByProductId(Long productId) {
        return selectList(IotThingModelDO::getProductId, productId);
    }

    default PageResult<IotThingModelDO> selectPage(IotThingModelPageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<IotThingModelDO>()
                .eqIfPresent(IotThingModelDO::getProductId, pageReq.getProductId())
                .likeIfPresent(IotThingModelDO::getName, pageReq.getName())
                .likeIfPresent(IotThingModelDO::getIdentifier, pageReq.getIdentifier())
                .eqIfPresent(IotThingModelDO::getType, pageReq.getType())
                .eqIfPresent(IotThingModelDO::getIsHistory, pageReq.getIsHistory())
        );
    }

}
