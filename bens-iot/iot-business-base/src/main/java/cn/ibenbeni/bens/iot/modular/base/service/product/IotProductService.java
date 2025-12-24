package cn.ibenbeni.bens.iot.modular.base.service.product;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductSaveReq;

import java.util.Set;

/**
 * IOT产品-服务
 */
public interface IotProductService {

    /**
     * 创建产品
     *
     * @return 产品ID
     */
    Long createProduct(IotProductSaveReq saveReq);

    /**
     * 删除产品
     *
     * @param productId 产品ID
     */
    void deleteProduct(Long productId);

    /**
     * 批量删除产品
     *
     * @param productIdSet 产品ID集合
     */
    void deleteProduct(Set<Long> productIdSet);

    /**
     * 修改产品
     */
    void updateProduct(IotProductSaveReq updateReq);

    /**
     * 修改产品状态
     *
     * @param productId     产品ID
     * @param productStatus 产品状态
     */
    void updateProductStatus(Long productId, Integer productStatus);

    /**
     * 获取产品详情
     *
     * @param productId 产品ID
     * @return 产品详情
     */
    IotProductDO getProduct(Long productId);

    /**
     * 获取产品详情
     * <p>注意：该方法会忽略租户条件。调用时，确认不会出现跨租户数据错误</p>
     *
     * @param productId 产品ID
     * @return 产品详情
     */
    IotProductDO getProductFromCache(Long productId);

    /**
     * 获取产品详情
     * <p>注意：该方法会忽略租户条件。调用时，确认不会出现跨租户数据错误</p>
     *
     * @param productKey 产品Key
     * @return 产品详情
     */
    IotProductDO getProductFromCache(String productKey);

    /**
     * 获取产品分页列表
     */
    PageResult<IotProductDO> pageProduct(IotProductPageReq pageReq);

    /**
     * 校验产品是否存在
     *
     * @param productId 产品ID
     * @return 产品信息
     */
    IotProductDO validateProductExists(Long productId);

}
