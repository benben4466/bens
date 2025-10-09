package cn.ibenbeni.bens.iot.modular.base.service.product;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductCategoryDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategoryPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategorySaveReq;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * IOT产品分类-服务实现类
 */
public interface IotProductCategoryService {

    /**
     * 创建产品分类
     *
     * @return 产品分类ID
     */
    Long createProductCategory(@Valid IotProductCategorySaveReq saveReq);

    /**
     * 删除产品分类
     *
     * @param categoryId 产品分类ID
     */
    void deleteProductCategory(Long categoryId);

    /**
     * 批量删除产品分类
     *
     * @param categoryIdSet 产品分类ID集合
     */
    void deleteProductCategory(Set<Long> categoryIdSet);

    /**
     * 修改产品分类
     */
    void updateProductCategory(@Valid IotProductCategorySaveReq saveReq);

    /**
     * 获取产品分类详情
     *
     * @param categoryId 产品分类ID
     * @return 产品分类详情
     */
    IotProductCategoryDO getProductCategory(Long categoryId);

    /**
     * 获取产品分类列表
     *
     * @param categoryIdSet 产品分类ID集合
     */
    List<IotProductCategoryDO> listProductCategory(Set<Long> categoryIdSet);

    /**
     * 获取产品分类分页列表
     */
    PageResult<IotProductCategoryDO> pageProductCategory(IotProductCategoryPageReq pageReq);

}
