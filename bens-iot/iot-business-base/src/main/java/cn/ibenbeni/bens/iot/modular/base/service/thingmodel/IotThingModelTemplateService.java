package cn.ibenbeni.bens.iot.modular.base.service.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelTemplateDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplatePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplateSaveReq;

import javax.validation.Valid;
import java.util.Set;

/**
 * IOT-物模型模板-服务
 */
public interface IotThingModelTemplateService {

    /**
     * 创建物模型模板
     *
     * @return 物模型模板ID
     */
    Long createThingModelTemplate(@Valid IotThingModelTemplateSaveReq saveReq);

    /**
     * 删除物模型模板
     *
     * @param templateId 物模型模板ID
     */
    void deleteThingModelTemplate(Long templateId);

    /**
     * 批量删除物模型模板
     *
     * @param templateIdSet 物模型模板ID集合
     */
    void deleteThingModelTemplate(Set<Long> templateIdSet);

    /**
     * 更新物模型模板
     */
    void updateThingModelTemplate(@Valid IotThingModelTemplateSaveReq updateReq);

    /**
     * 获取物模型模板
     *
     * @param templateId 物模型模板ID
     */
    IotThingModelTemplateDO getThingModelTemplate(Long templateId);

    /**
     * 获取物模型模板分页列表
     */
    PageResult<IotThingModelTemplateDO> pageThingModelTemplate(IotThingModelTemplatePageReq pageReq);

}
