package cn.ibenbeni.bens.iot.modular.base.service.thingmodel.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelTemplateDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.mysql.thingmodel.IotThingModelTemplateMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplatePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplateSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.thingmodel.IotThingModelTemplateService;
import cn.ibenbeni.bens.rule.enums.IsSysEnum;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * IOT-物模型模板-服务实现类
 */
@Slf4j
@Service
public class IotThingModelTemplateServiceImpl implements IotThingModelTemplateService {

    @Resource
    private IotThingModelTemplateMapper thingModelTemplateMapper;

    // region 公共方法

    @Override
    public Long createThingModelTemplate(IotThingModelTemplateSaveReq saveReq) {
        // 校验模型模板标识唯一
        validateIdentifierUnique(null, saveReq.getIdentifier());
        // 校验模型名称唯一
        validateNameUnique(null, saveReq.getName());

        // 入库
        IotThingModelTemplateDO thingModelTemplate = BeanUtil.toBean(saveReq, IotThingModelTemplateDO.class);
        thingModelTemplateMapper.insert(thingModelTemplate);

        return thingModelTemplate.getTemplateId();
    }

    @Override
    public void deleteThingModelTemplate(Long templateId) {
        // 校验物模型模板是否存在
        IotThingModelTemplateDO thingModelTemplate = validateThingModelTemplateExists(templateId);
        // 内置物模型禁止删除
        if (IsSysEnum.isSys(thingModelTemplate.getIsSys())) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_SYS_PROHIBIT_DELETE);
        }

        thingModelTemplateMapper.deleteById(templateId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteThingModelTemplate(Set<Long> templateIdSet) {
        List<IotThingModelTemplateDO> list = thingModelTemplateMapper.selectByIds(templateIdSet);
        if (CollUtil.isEmpty(list) || list.size() != templateIdSet.size()) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_NOT_EXISTS);
        }
        // 内置物模型禁止删除
        for (IotThingModelTemplateDO thingModelTemplate : list) {
            if (IsSysEnum.isSys(thingModelTemplate.getIsSys())) {
                throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_SYS_PROHIBIT_DELETE);
            }
        }

        thingModelTemplateMapper.deleteByIds(templateIdSet);
    }

    @Override
    public void updateThingModelTemplate(IotThingModelTemplateSaveReq updateReq) {
        // 校验物模型模板存在
        validateThingModelTemplateExists(updateReq.getTemplateId());
        // 校验模型模板标识唯一
        validateIdentifierUnique(updateReq.getTemplateId(), updateReq.getIdentifier());
        // 校验模型名称唯一
        validateNameUnique(updateReq.getTemplateId(), updateReq.getName());

        // 更新数据库
        IotThingModelTemplateDO updateDO = BeanUtil.toBean(updateReq, IotThingModelTemplateDO.class);
        thingModelTemplateMapper.updateById(updateDO);
    }

    @Override
    public IotThingModelTemplateDO getThingModelTemplate(Long templateId) {
        return thingModelTemplateMapper.selectById(templateId);
    }

    @Override
    public PageResult<IotThingModelTemplateDO> pageThingModelTemplate(IotThingModelTemplatePageReq pageReq) {
        return thingModelTemplateMapper.selectPage(pageReq);
    }

    // endregion

    // region 私有方法

    /**
     * 校验模型模板标识唯一
     * <p>模型模板必须唯一</p>
     *
     * @param templateId 模型模板ID
     * @param identifier 模型标识
     */
    private void validateIdentifierUnique(Long templateId, String identifier) {
        IotThingModelTemplateDO thingModelTemplate = thingModelTemplateMapper.selectByIdentifier(identifier);
        if (thingModelTemplate == null) {
            return;
        }

        if (templateId == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_IDENTIFIER_EXISTS);
        }
        if (!thingModelTemplate.getTemplateId().equals(templateId)) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_IDENTIFIER_EXISTS);
        }
    }

    /**
     * 校验模型名称唯一
     *
     * @param templateId 模型模板ID
     * @param name       模型名称
     */
    private void validateNameUnique(Long templateId, String name) {
        IotThingModelTemplateDO thingModelTemplate = thingModelTemplateMapper.selectByName(name);
        if (thingModelTemplate == null) {
            return;
        }

        if (templateId == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_NAME_EXISTS);
        }
        if (!thingModelTemplate.getTemplateId().equals(templateId)) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_NAME_EXISTS);
        }
    }

    private IotThingModelTemplateDO validateThingModelTemplateExists(Long templateId) {
        IotThingModelTemplateDO iotThingModelTemplate = thingModelTemplateMapper.selectById(templateId);
        if (iotThingModelTemplate == null) {
            throw new IotException(IotExceptionEnum.THING_MODEL_TEMPLATE_NOT_EXISTS);
        }
        return iotThingModelTemplate;
    }

    // endregion

}
