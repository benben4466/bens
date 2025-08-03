package cn.ibenbeni.bens.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.config.api.enums.ConfigTypeEnum;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.mapper.SysConfigMapper;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigPageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigSaveReq;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
import cn.ibenbeni.bens.config.modular.service.SysConfigTypeService;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 参数配置服务实现类
 *
 * @author: benben
 * @time: 2025/6/18 上午10:32
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigDO> implements SysConfigService {

    // region 参数校验分组

    @Resource(name = "configValueCache")
    private CacheOperatorApi<String> configValueCache;

    @Resource
    private SysConfigMapper configMapper;

    @Lazy
    @Resource
    private SysConfigTypeService configTypeService;

    // endregion

    // region 参数校验分组

    @Override
    public Long createConfig(SysConfigSaveReq req) {
        // 检查参数类型是否存在
        configTypeService.validateConfigTypeExists(req.getConfigTypeCode());
        // 校验参数编码唯一
        validateConfigCodeUnique(null, req.getConfigCode());

        SysConfigDO config = BeanUtil.toBean(req, SysConfigDO.class);
        save(config);
        return config.getConfigId();
    }

    @Override
    public void deleteConfig(Long configId) {
        SysConfigDO config = validateConfigExists(configId);
        if (ConfigTypeEnum.SYSTEM.getTypeCode().equals(config.getConfigType())) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        removeById(configId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteConfigList(Set<Long> configIdSet) {
        List<SysConfigDO> configList = listByIds(configIdSet);
        configList.forEach(config -> {
            if (ConfigTypeEnum.SYSTEM.getTypeCode().equals(config.getConfigType())) {
                throw new ConfigException(ConfigExceptionEnum.CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
            }
        });

        removeByIds(configIdSet);
    }

    @Override
    public void updateConfig(SysConfigSaveReq req) {
        // 校验参数是否存在
        SysConfigDO config = validateConfigExists(req.getConfigId());
        // 校验参数编码是否存在
        validateConfigCodeUnique(req.getConfigId(), req.getConfigCode());

        BeanUtil.copyProperties(req, config);
        updateById(config);
    }

    @Override
    public SysConfigDO getConfig(Long configId) {
        return getById(configId);
    }

    @Override
    public SysConfigDO getConfigByCode(String configCode) {
        return configMapper.getConfigByCode(configCode);
    }

    @Override
    public long countByConfigTypeCode(String configTypeCode) {
        return configMapper.countByConfigTypeCode(configTypeCode);
    }

    @Override
    public List<SysConfigDO> listByConfigTypeCode(Set<String> configTypeCode) {
        return configMapper.listByConfigTypeCode(configTypeCode);
    }

    @Override
    public PageResult<SysConfigDO> getConfigPage(SysConfigPageReq req) {
        return configMapper.selectPage(req);
    }

    @Override
    public <T> T getConfigValueNullable(String configCode, Class<T> clazz) {
        String configValue = configValueCache.get(configCode);
        if (StrUtil.isNotBlank(configValue)) {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception ex) {
                String format = StrUtil.format(ConfigExceptionEnum.CONVERT_ERROR.getUserTip(), configCode, configValue, clazz.toString());
                log.warn(format);
                return null;
            }
        } else {
            String format = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), configCode);
            log.warn(format);
            return null;
        }
    }

    @Override
    public <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        T configValue = this.getConfigValueNullable(configCode, clazz);
        return ObjectUtil.isNotEmpty(configValue) ? configValue : defaultValue;
    }

    // endregion

    // region 私有方法

    private void validateConfigCodeUnique(Long configId, String configCode) {
        SysConfigDO config = configMapper.getConfigByCode(configCode);
        if (config == null) {
            return;
        }
        if (configId == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_CODE_DUPLICATE);
        }
        if (!config.getConfigId().equals(configId)) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_CODE_DUPLICATE);
        }
    }

    private SysConfigDO validateConfigExists(Long configId) {
        SysConfigDO config = getById(configId);
        if (config == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST);
        }
        return config;
    }

    // endregion

}
