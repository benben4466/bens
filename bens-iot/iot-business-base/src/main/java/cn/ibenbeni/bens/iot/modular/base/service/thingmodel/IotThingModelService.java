package cn.ibenbeni.bens.iot.modular.base.service.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelSaveReq;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * IOT-物模型-服务
 */
public interface IotThingModelService {

    /**
     * 创建物模型
     *
     * @return 物模型ID
     */
    Long createThingModel(@Valid IotThingModelSaveReq saveReq);

    /**
     * 批量创建物模型
     *
     * @param saveReqs 创建物模型集合
     */
    void createBatchThingModel(@Valid List<IotThingModelSaveReq> saveReqs);

    /**
     * 删除物模型
     *
     * @param modelId 物模型ID
     */
    void deleteThingModel(Long modelId);

    /**
     * 批量删除物模型模板
     *
     * @param modelIdSet 物模型模板ID集合
     */
    void deleteThingModel(Set<Long> modelIdSet);

    /**
     * 更新物模型模板
     */
    void updateThingModel(@Valid IotThingModelSaveReq updateReq);

    /**
     * 获取物模型模板
     *
     * @param modelId 物模型ID
     */
    IotThingModelDO getThingModel(Long modelId);

    /**
     * 根据产品ID，获取物模型
     *
     * @param productId 产品ID
     */
    List<IotThingModelDO> listByProductId(Long productId);

    /**
     * 获取物模型分页列表
     */
    PageResult<IotThingModelDO> pageThingModel(IotThingModelPageReq pageReq);

}
