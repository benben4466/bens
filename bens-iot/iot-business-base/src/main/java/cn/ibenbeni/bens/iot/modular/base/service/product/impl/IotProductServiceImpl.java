package cn.ibenbeni.bens.iot.modular.base.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.enums.product.IotProductStatusEnum;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.product.IotProductMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.device.property.IotDevicePropertyService;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.rule.enums.IsSysEnum;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * IOT产品分类-服务实现
 */
@Slf4j
@Service
public class IotProductServiceImpl implements IotProductService {

    @Resource
    private IotProductMapper productMapper;

    @Resource
    private IotDeviceService deviceService;

    @Resource
    private IotDevicePropertyService devicePropertyService;

    // region 公共方法

    @Override
    public Long createProduct(IotProductSaveReq saveReq) {
        // 校验产品Key是否重复
        validateProductKeyDuplicate(null, saveReq.getProductKey());

        IotProductDO product = BeanUtil.toBean(saveReq, IotProductDO.class);
        productMapper.insert(product);
        return product.getProductId();
    }

    @Override
    public void deleteProduct(Long productId) {
        // 校验是否存在
        IotProductDO iotProduct = validateProductExists(productId);
        // 系统内置不允许删除
        if (IsSysEnum.isSys(iotProduct.getIsSys())) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_ALLOW_DELETE);
        }
        // 校验产品下是否存在设备
        if (deviceService.getDeviceCountByProductId(productId) > 0) {
            throw new IotException(IotExceptionEnum.PRODUCT_DELETE_FAIL_HAS_DEVICE);
        }

        productMapper.deleteById(productId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteProduct(Set<Long> productIdSet) {
        List<IotProductDO> iotProducts = productMapper.selectByIds(productIdSet);
        // 校验是否存在
        if (iotProducts.size() != productIdSet.size()) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_EXISTED);
        }

        Map<Long, Long> deviceCountMap = deviceService.getDeviceCountByProductId(productIdSet);
        for (IotProductDO iotProduct : iotProducts) {
            // 系统内置不允许删除
            if (IsSysEnum.isSys(iotProduct.getIsSys())) {
                throw new IotException(IotExceptionEnum.PRODUCT_NOT_ALLOW_DELETE);
            }
            // 校验产品下是否存在设备
            Long deviceCount = deviceCountMap.get(iotProduct.getProductId());
            if (deviceCount != null && deviceCount > 0) {
                throw new IotException(IotExceptionEnum.PRODUCT_DELETE_FAIL_HAS_DEVICE);
            }
        }

        productMapper.deleteByIds(productIdSet);
    }

    @Override
    public void updateProduct(IotProductSaveReq updateReq) {
        // 校验产品Key是否重复
        validateProductKeyDuplicate(updateReq.getProductId(), updateReq.getProductKey());

        IotProductDO updateIotProduct = BeanUtil.toBean(updateReq, IotProductDO.class);
        productMapper.updateById(updateIotProduct);
    }

    @Override
    public void updateProductStatus(Long productId, Integer productStatus) {
        // 校验产品是否存在
        validateProductExists(productId);

        // 产品状态=发布，需要创建/修改数据定义
        if (Objects.equals(productStatus, IotProductStatusEnum.PUBLISHED.getStatus())) {
            devicePropertyService.defineDevicePropertyData(productId);
        }

        // 更新状态
        IotProductDO updateDO = IotProductDO.builder()
                .productId(productId)
                .statusFlag(productStatus)
                .build();
        productMapper.updateById(updateDO);
    }

    @Override
    public IotProductDO getProduct(Long productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public IotProductDO getProduct(String productKey) {
        return productMapper.selectByKey(productKey);
    }

    @Override
    public PageResult<IotProductDO> pageProduct(IotProductPageReq pageReq) {
        return productMapper.selectPage(pageReq);
    }

    @Override
    public IotProductDO validateProductExists(Long productId) {
        IotProductDO iotProduct = productMapper.selectById(productId);
        if (iotProduct == null) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_EXISTED);
        }

        return iotProduct;
    }

    // endregion

    // region 私有方法

    private void validateProductKeyDuplicate(Long productId, String productKey) {
        IotProductDO iotProduct = productMapper.selectByKey(productKey);
        if (iotProduct == null) {
            return;
        }

        if (productId == null) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        if (!iotProduct.getProductId().equals(productId)) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_EXISTED);
        }
    }

    // endregion

}
