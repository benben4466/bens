package cn.ibenbeni.bens.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.config.api.ConfigInitCallbackApi;
import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import cn.ibenbeni.bens.config.api.context.ConfigContext;
import cn.ibenbeni.bens.config.api.enums.ConfigTypeEnum;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.mapper.SysConfigMapper;
import cn.ibenbeni.bens.config.modular.pojo.request.ConfigInitReq;
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
import java.util.Map;
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

        // 清除缓存
        ConfigContext.me().deleteConfig(config.getConfigCode());
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteConfigList(Set<Long> configIdSet) {
        List<SysConfigDO> configList = listByIds(configIdSet);
        configList.forEach(config -> {
            if (ConfigTypeEnum.SYSTEM.getTypeCode().equals(config.getConfigType())) {
                throw new ConfigException(ConfigExceptionEnum.CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
            }

            // 清除缓存
            ConfigContext.me().deleteConfig(config.getConfigCode());
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

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void initConfig(ConfigInitReq req) {
        // 检查参数是否已经初始化
        Boolean alreadyInit = getInitConfigFlag();
        if (alreadyInit) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_INIT_ALREADY);
        }

        Map<String, ConfigInitCallbackApi> configInitBeanMap = SpringUtil.getBeansOfType(ConfigInitCallbackApi.class);
        // 调用初始化之前回调
        if (CollUtil.isNotEmpty(configInitBeanMap)) {
            for (ConfigInitCallbackApi configInitBean : configInitBeanMap.values()) {
                configInitBean.initBefore();
            }
        }

        // 将系统初始化标识设置为true
        Map<String, String> sysConfigMap = req.getSysConfigMap();
        sysConfigMap.put(ConfigConstants.SYSTEM_CONFIG_INIT_FLAG_CODE, Boolean.TRUE.toString());

        // 更新配置值
        for (Map.Entry<String, String> entry : sysConfigMap.entrySet()) {
            String configCode = entry.getKey();
            String configValue = entry.getValue();

            configMapper.updateValueByCode(configCode, configValue);

            // 更新缓存
            ConfigContext.me().putConfig(configCode, configValue);
        }

        // 调用初始化之后回调
        if (CollUtil.isNotEmpty(configInitBeanMap)) {
            for (ConfigInitCallbackApi configInitBean : configInitBeanMap.values()) {
                configInitBean.initAfter();
            }
        }
    }

    @Override
    public Boolean getInitConfigFlag() {
        return configMapper.getInitConfigFlag();
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
