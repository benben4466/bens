package cn.ibenbeni.bens.iot.modular.base.mapper.product;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductCategoryDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategoryPageReq;

/**
 * IOT产品分类-Mapper
 */
public interface IotProductCategoryMapper extends BaseMapperX<IotProductCategoryDO> {

    default IotProductCategoryDO selectByCode(String categoryCode) {
        return selectOne(new LambdaQueryWrapperX<IotProductCategoryDO>()
                .eq(IotProductCategoryDO::getCategoryCode, categoryCode)
        );
    }

    default PageResult<IotProductCategoryDO> selectPage(IotProductCategoryPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<IotProductCategoryDO>()
                .likeIfPresent(IotProductCategoryDO::getCategoryName, req.getCategoryName())
                .likeIfPresent(IotProductCategoryDO::getCategoryCode, req.getCategoryCode())
                .eqIfPresent(IotProductCategoryDO::getCreateTime, req.getCreateTime())
                .orderByDesc(IotProductCategoryDO::getCategoryId)
        );
    }

}
