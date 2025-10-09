package cn.ibenbeni.bens.iot.modular.base.service.product.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductCategoryDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.product.IotProductCategoryMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategoryPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategorySaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductCategoryService;
import cn.ibenbeni.bens.rule.enums.IsSysEnum;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * IOT产品分类-服务实现类
 */
@Slf4j
@Service
public class IotProductCategoryServiceImpl implements IotProductCategoryService {

    @Resource
    private IotProductCategoryMapper productCategoryMapper;

    // region 公共方法

    @Override
    public Long createProductCategory(IotProductCategorySaveReq saveReq) {
        // 校验产品编码是否重复
        validateProductCategoryCodeDuplicate(null, saveReq.getCategoryCode());

        IotProductCategoryDO productCategory = BeanUtil.toBean(saveReq, IotProductCategoryDO.class);
        productCategoryMapper.insert(productCategory);
        return productCategory.getCategoryId();
    }

    @Override
    public void deleteProductCategory(Long categoryId) {
        // 校验是否存在
        IotProductCategoryDO productCategory = validateProductCategoryExists(categoryId);
        // 校验是否是系统产品分类
        if (IsSysEnum.isSys(productCategory.getIsSys())) {
            throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_ALLOW_DELETE);
        }

        // TODO [思考]需要校验产品分类下是否存在产品，有的话不许删除
        productCategoryMapper.deleteById(categoryId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductCategory(Set<Long> categoryIdSet) {
        for (Long categoryId : categoryIdSet) {
            IotProductCategoryDO productCategory = validateProductCategoryExists(categoryId);
            // 校验是否是系统产品分类
            if (IsSysEnum.isSys(productCategory.getIsSys())) {
                throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_ALLOW_DELETE);
            }
            productCategoryMapper.deleteById(categoryId);
        }
    }

    @Override
    public void updateProductCategory(IotProductCategorySaveReq saveReq) {
        // 校验是否存在
        IotProductCategoryDO productCategory = validateProductCategoryExists(saveReq.getCategoryId());
        // 校验产品编码是否重复
        validateProductCategoryCodeDuplicate(saveReq.getCategoryId(), saveReq.getCategoryCode());
        // 若产品分类是系统内置，则不允许修改系统内置标识
        if (IsSysEnum.isSys(productCategory.getIsSys()) && !IsSysEnum.isSys(saveReq.getIsSys())) {
            throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_ALLOW_UPDATE_SYS_FLAG);
        }

        IotProductCategoryDO updateProductCategory = BeanUtil.toBean(saveReq, IotProductCategoryDO.class);
        productCategoryMapper.updateById(updateProductCategory);
    }

    @Override
    public IotProductCategoryDO getProductCategory(Long categoryId) {
        return productCategoryMapper.selectById(categoryId);
    }

    @Override
    public List<IotProductCategoryDO> listProductCategory(Set<Long> categoryIdSet) {
        return productCategoryMapper.selectByIds(categoryIdSet);
    }

    @Override
    public PageResult<IotProductCategoryDO> pageProductCategory(IotProductCategoryPageReq pageReq) {
        return productCategoryMapper.selectPage(pageReq);
    }

    // endregion

    // region 公共方法

    private void validateProductCategoryCodeDuplicate(Long categoryId, String categoryCode) {
        IotProductCategoryDO productCategory = productCategoryMapper.selectByCode(categoryCode);
        if (productCategory == null) {
            return;
        }

        if (categoryId == null) {
            throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_EXISTED);
        }
        if (!productCategory.getCategoryId().equals(categoryId)) {
            throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_EXISTED);
        }

    }

    private IotProductCategoryDO validateProductCategoryExists(Long categoryId) {
        IotProductCategoryDO productCategory = productCategoryMapper.selectById(categoryId);
        if (productCategory == null) {
            throw new IotException(IotExceptionEnum.PRODUCT_CATEGORY_NOT_EXISTED);
        }
        return productCategory;
    }

    // endregion

}
