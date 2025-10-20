package cn.ibenbeni.bens.iot.modular.base.service.thingmodel.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.enums.product.IotProductStatusEnum;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.thingmodel.IotThingModelMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.iot.modular.base.service.thingmodel.IotThingModelService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * IOT-物模型-服务实现类
 */
@Slf4j
@Service
public class IotThingModelServiceImpl implements IotThingModelService {

    @Resource
    private IotThingModelMapper thingModelMapper;

    @Resource
    private IotProductService productService;

    // region 公共方法

    @Override
    public Long createThingModel(IotThingModelSaveReq saveReq) {
        // 校验模型标识唯一
        validateIdentifierUnique(null, saveReq.getIdentifier());
        // 校验模型名称唯一
        validateNameUnique(null, saveReq.getName());
        // 校验产品状态
        validateProductStatus(saveReq.getProductId());

        // 入库
        IotThingModelDO thingModel = BeanUtil.toBean(saveReq, IotThingModelDO.class);
        thingModelMapper.insert(thingModel);

        return thingModel.getModelId();
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void createBatchThingModel(List<IotThingModelSaveReq> saveReqs) {
        for (IotThingModelSaveReq saveReq : saveReqs) {
            createThingModel(saveReq);
        }
    }

    @Override
    public void deleteThingModel(Long modelId) {
        // 校验物模型模板是否存在
        IotThingModelDO thingModel = validateThingModelExists(modelId);
        // 校验产品状态
        validateProductStatus(thingModel.getProductId());

        thingModelMapper.deleteById(thingModel);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteThingModel(Set<Long> modelIdSet) {
        List<IotThingModelDO> list = thingModelMapper.selectByIds(modelIdSet);
        if (CollUtil.isEmpty(list) || list.size() != modelIdSet.size()) {
            throw new IotException(IotExceptionEnum.THING_MODEL_NOT_EXISTS);
        }
        for (IotThingModelDO thingModel : list) {
            // 校验产品状态
            validateProductStatus(thingModel.getProductId());
        }

        thingModelMapper.deleteByIds(modelIdSet);
    }

    @Override
    public void updateThingModel(IotThingModelSaveReq updateReq) {
        // 校验物模型存在
        validateThingModelExists(updateReq.getModelId());
        // 校验模型标识唯一
        validateIdentifierUnique(updateReq.getModelId(), updateReq.getIdentifier());
        // 校验模型名称唯一
        validateNameUnique(updateReq.getModelId(), updateReq.getName());
        // 校验产品状态
        validateProductStatus(updateReq.getProductId());

        // 更新数据库
        IotThingModelDO updateDO = BeanUtil.toBean(updateReq, IotThingModelDO.class);
        thingModelMapper.updateById(updateDO);
    }

    @Override
    public IotThingModelDO getThingModel(Long modelId) {
        return thingModelMapper.selectById(modelId);
    }

    @Override
    public PageResult<IotThingModelDO> pageThingModel(IotThingModelPageReq pageReq) {
        return thingModelMapper.selectPage(pageReq);
    }

    // endregion

    // region 私有方法

    /**
     * 校验模型标识唯一
     * <p>同产品下唯一</p>
     *
     * @param modelId    模型ID
     * @param identifier 模型标识
     */
    private void validateIdentifierUnique(Long modelId, String identifier) {
        IotThingModelDO thingModel = thingModelMapper.selectByIdentifier(identifier);
        if (thingModel == null) {
            return;
        }

        if (modelId == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_IDENTIFIER_EXISTS);
        }
        if (!thingModel.getModelId().equals(modelId)) {
            throw new IotException(IotExceptionEnum.THING_MODEL_IDENTIFIER_EXISTS);
        }
    }

    /**
     * 校验模型名称唯一
     *
     * @param modelId 模型ID
     * @param name    模型名称
     */
    private void validateNameUnique(Long modelId, String name) {
        IotThingModelDO thingModel = thingModelMapper.selectByName(name);
        if (thingModel == null) {
            return;
        }

        if (modelId == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_NAME_EXISTS);
        }
        if (!thingModel.getModelId().equals(modelId)) {
            throw new IotException(IotExceptionEnum.THING_MODEL_NAME_EXISTS);
        }
    }

    private IotThingModelDO validateThingModelExists(Long modelId) {
        IotThingModelDO iotThingModel = thingModelMapper.selectById(modelId);
        if (iotThingModel == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_NOT_EXISTS);
        }
        return iotThingModel;
    }

    private void validateProductStatus(Long productId) {
        IotProductDO product = productService.getProduct(productId);
        if (IotProductStatusEnum.PUBLISHED.getStatus().equals(product.getStatusFlag())) {
            throw new IotException(IotExceptionEnum.PRODUCT_STATUS_NOT_ALLOW_THING_MODEL);
        }
    }

    // endregion

}
