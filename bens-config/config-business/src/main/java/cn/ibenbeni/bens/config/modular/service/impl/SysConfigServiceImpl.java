package cn.ibenbeni.bens.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfig;
import cn.ibenbeni.bens.config.modular.mapper.SysConfigMapper;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigRequest;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 参数配置服务实现类
 *
 * @author: benben
 * @time: 2025/6/18 上午10:32
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Resource(name = "configValueCache")
    private CacheOperatorApi<String> configValueCache;

    @Override
    public void add(SysConfigRequest sysConfigRequest) {
        SysConfig sysConfig = BeanUtil.toBean(sysConfigRequest, SysConfig.class);
        this.save(sysConfig);

        // 更新缓存
        this.putConfig(sysConfigRequest.getConfigCode(), sysConfigRequest.getConfigValue());
    }

    @Override
    public void del(SysConfigRequest sysConfigRequest) {
        SysConfig dbSysConfig = this.querySysConfig(sysConfigRequest.getConfigId());

        // 不允许删除系统参数
        if (YesOrNotEnum.Y.getCode().equals(dbSysConfig.getSysFlag())) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
        }
        this.removeById(dbSysConfig.getConfigId());

        // 清除缓存
        this.deleteConfig(dbSysConfig.getConfigCode());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(SysConfigRequest sysConfigRequest) {
        for (Long configId : sysConfigRequest.getConfigIdList()) {
            SysConfig dbSysConfig = this.querySysConfig(configId);

            // 不允许删除系统参数
            if (YesOrNotEnum.Y.getCode().equals(dbSysConfig.getSysFlag())) {
                throw new ConfigException(ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
            }
            this.removeById(dbSysConfig.getConfigId());

            // 清除缓存
            this.deleteConfig(dbSysConfig.getConfigCode());
        }
    }

    @Override
    public void delByConfigTypeCode(String configTypeCode) {
        if (StrUtil.isBlank(configTypeCode)) {
            return;
        }
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery(SysConfig.class)
                .eq(SysConfig::getConfigTypeCode, configTypeCode);
        this.remove(queryWrapper);
    }

    @Override
    public void edit(SysConfigRequest sysConfigRequest) {
        SysConfig dbSysConfig = this.querySysConfig(sysConfigRequest.getConfigId());
        BeanUtil.copyProperties(sysConfigRequest, dbSysConfig);

        // 不允许修改参数配置编码、参数配置类型编码
        dbSysConfig.setConfigTypeCode(null);
        dbSysConfig.setConfigCode(null);

        this.updateById(dbSysConfig);

        // 更新缓存
        this.putConfig(sysConfigRequest.getConfigCode(), sysConfigRequest.getConfigValue());
    }

    @Override
    public SysConfig detail(SysConfigRequest sysConfigRequest) {
        return this.querySysConfig(sysConfigRequest.getConfigId());
    }

    @Override
    public List<SysConfig> findList(SysConfigRequest sysConfigRequest) {
        LambdaQueryWrapper<SysConfig> queryWrapper = this.createWrapper(sysConfigRequest)
                .select(SysConfig::getConfigId, SysConfig::getConfigName, SysConfig::getConfigCode, SysConfig::getConfigValue, SysConfig::getSysFlag);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysConfig> findPage(SysConfigRequest sysConfigRequest) {
        LambdaQueryWrapper<SysConfig> queryWrapper = this.createWrapper(sysConfigRequest)
                .select(SysConfig::getConfigId, SysConfig::getConfigName, SysConfig::getConfigCode, SysConfig::getConfigValue, SysConfig::getSysFlag);
        Page<SysConfig> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public String getConfigValueByConfigCode(String configCode) {
        if (StrUtil.isBlank(configCode)) {
            return null;
        }
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery(SysConfig.class)
                .eq(SysConfig::getConfigCode, configCode);
        SysConfig dbSysConfig = this.getOne(queryWrapper, false);
        return dbSysConfig != null ? dbSysConfig.getConfigValue() : null;
    }

    private SysConfig querySysConfig(Long configId) {
        SysConfig dbSysConfig = this.getById(configId);
        if (dbSysConfig == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_NOT_EXIST, "id: " + configId);
        }
        return dbSysConfig;
    }

    private LambdaQueryWrapper<SysConfig> createWrapper(SysConfigRequest sysConfigRequest) {
        return Wrappers.lambdaQuery(SysConfig.class)
                .eq(StrUtil.isNotBlank(sysConfigRequest.getConfigTypeCode()), SysConfig::getConfigTypeCode, sysConfigRequest.getConfigTypeCode())
                .eq(StrUtil.isNotBlank(sysConfigRequest.getConfigCode()), SysConfig::getConfigCode, sysConfigRequest.getConfigCode())
                .eq(StrUtil.isNotBlank(sysConfigRequest.getConfigName()), SysConfig::getConfigName, sysConfigRequest.getConfigName())
                .eq(ObjectUtil.isNotEmpty(sysConfigRequest.getSysFlag()), SysConfig::getSysFlag, sysConfigRequest.getSysFlag())
                .orderByAsc(SysConfig::getConfigSort);
    }

    @Override
    public void putConfig(String key, String value) {
        configValueCache.put(key, value);
    }

    @Override
    public void deleteConfig(String key) {
        configValueCache.remove(key);
    }

}
