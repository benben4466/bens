package cn.ibenbeni.bens.iot.modular.base.mapper.product;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductPageReq;

/**
 * IOT产品-Mapper
 */
public interface IotProductMapper extends BaseMapperX<IotProductDO> {

    default IotProductDO selectByKey(String productKey) {
        return selectOne(IotProductDO::getProductKey, productKey);
    }

    default PageResult<IotProductDO> selectPage(IotProductPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<IotProductDO>()
                .likeIfPresent(IotProductDO::getProductName, req.getProductName())
                .likeIfPresent(IotProductDO::getProductKey, req.getProductKey())
                .likeIfPresent(IotProductDO::getCategoryName, req.getCategoryName())
                .eqIfPresent(IotProductDO::getStatusFlag, req.getStatusFlag())
                .betweenIfPresent(IotProductDO::getCreateTime, req.getCreateTime())
                .orderByDesc(IotProductDO::getProductId)
        );
    }

}
