package cn.ibenbeni.bens.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.config.api.enums.ConfigTypeEnum;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.entity.SysConfigTypeDO;
import cn.ibenbeni.bens.config.modular.mapper.SysConfigTypeMapper;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypePageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeSaveReq;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
import cn.ibenbeni.bens.config.modular.service.SysConfigTypeService;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 参数配置类型服务实现类
 *
 * @author: benben
 * @time: 2025/6/18 下午10:33
 */
@Service
public class SysConfigTypeServiceImpl extends ServiceImpl<SysConfigTypeMapper, SysConfigTypeDO> implements SysConfigTypeService {

    // region 属性

    @Resource
    private SysConfigTypeMapper configTypeMapper;

    @Resource
    private SysConfigService configService;

    // endregion

    // region 公共方法

    @Override
    public Long createConfigType(SysConfigTypeSaveReq req) {
        // 校验参数配置类型编码是否唯一
        validateConfigTypeCodeUnique(null, req.getConfigTypeCode());
        // 校验参数配置类型是否合法
        validateConfigType(req.getConfigType());

        SysConfigTypeDO configType = BeanUtil.toBean(req, SysConfigTypeDO.class);
        save(configType);
        return configType.getConfigTypeId();
    }

    @Override
    public void deleteConfigType(Long configTypeId) {
        SysConfigTypeDO configType = validateConfigTypeExists(configTypeId);
        if (ConfigTypeEnum.SYSTEM.getTypeCode().equals(configType.getConfigType())) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        // 校验参数配置类型下是否存在参数配置
        long configCount = configService.countByConfigTypeCode(configType.getConfigTypeCode());
        if (configCount > 0) {
            throw new ConfigException(ConfigExceptionEnum.DICT_TYPE_HAS_CHILDREN);
        }

        removeById(configTypeId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteConfigType(Set<Long> configTypeIdSet) {
        if (CollUtil.isEmpty(configTypeIdSet)) {
            return;
        }
        // 检查参数类型是否可删除
        List<SysConfigTypeDO> configTypeList = listByIds(configTypeIdSet);
        configTypeList.forEach(configType -> {
            if (ConfigTypeEnum.SYSTEM.getTypeCode().equals(configType.getConfigType())) {
                throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_CAN_NOT_DELETE_SYSTEM_TYPE);
            }
        });

        Set<String> configTypeCodeSet = CollectionUtils.convertSet(configTypeList, SysConfigTypeDO::getConfigTypeCode);
        List<SysConfigDO> configList = configService.listByConfigTypeCode(configTypeCodeSet);
        Map<String, Long> configGroupMap = CollectionUtils.convertMapGroup(configList, SysConfigDO::getConfigTypeCode);
        configTypeCodeSet.forEach(configTypeCode -> {
            Long configCount = configGroupMap.get(configTypeCode);
            if (configCount != null && configCount > 0) {
                throw new ConfigException(ConfigExceptionEnum.DICT_TYPE_HAS_CHILDREN);
            }
        });
        removeByIds(configTypeIdSet);
    }

    @Override
    public void updateConfigType(SysConfigTypeSaveReq req) {
        // 校验参数配置类型是否存在
        SysConfigTypeDO configType = validateConfigTypeExists(req.getConfigTypeId());
        // 校验参数配置类型编码是否唯一
        validateConfigTypeCodeUnique(req.getConfigTypeId(), req.getConfigTypeCode());

        BeanUtil.copyProperties(req, configType);
        updateById(configType);
    }

    @Override
    public SysConfigTypeDO getConfigType(Long configTypeId) {
        return getById(configTypeId);
    }

    @Override
    public SysConfigTypeDO getConfigTypeByCode(String configTypeCode) {
        return configTypeMapper.selectByCode(configTypeCode);
    }

    @Override
    public PageResult<SysConfigTypeDO> getConfigTypePage(SysConfigTypePageReq req) {
        return configTypeMapper.selectPage(req);
    }

    @Override
    public SysConfigTypeDO validateConfigTypeExists(String configTypeCode) throws ConfigException {
        SysConfigTypeDO configType = configTypeMapper.selectByCode(configTypeCode);
        if (configType == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_NOT_EXIST);
        }
        return configType;
    }

    // endregion

    // region 私有方法

    /**
     * 校验参数配置类型编码是否唯一
     *
     * @param configTypeId   参数配置类型ID
     * @param configTypeCode 参数配置类型编码
     */
    private void validateConfigTypeCodeUnique(Long configTypeId, String configTypeCode) {
        SysConfigTypeDO configType = configTypeMapper.selectByCode(configTypeCode);
        if (configType == null) {
            return;
        }

        if (configTypeId == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_CODE_DUPLICATE);
        }
        if (!configType.getConfigTypeId().equals(configTypeId)) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_CODE_DUPLICATE);
        }
    }

    /**
     * 校验参数配置类型值合法
     */
    private void validateConfigType(Integer configType) {
        if (ConfigTypeEnum.parseToEnum(configType) == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_PARAM_ERROR);
        }
    }

    private SysConfigTypeDO validateConfigTypeExists(Long configTypeId) {
        SysConfigTypeDO configType = getById(configTypeId);
        if (configType == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_NOT_EXIST);
        }
        return configType;
    }

    // endregion

}
