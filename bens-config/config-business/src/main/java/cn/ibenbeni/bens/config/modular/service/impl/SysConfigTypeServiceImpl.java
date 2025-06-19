package cn.ibenbeni.bens.config.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfigType;
import cn.ibenbeni.bens.config.modular.mapper.SysConfigTypeMapper;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeRequest;
import cn.ibenbeni.bens.config.modular.service.SysConfigTypeService;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置类型服务实现类
 *
 * @author: benben
 * @time: 2025/6/18 下午10:33
 */
@Service
public class SysConfigTypeServiceImpl extends ServiceImpl<SysConfigTypeMapper, SysConfigType> implements SysConfigTypeService {

    @Override
    public void add(SysConfigTypeRequest sysConfigTypeRequest) {
        SysConfigType sysConfigType = BeanUtil.toBean(sysConfigTypeRequest, SysConfigType.class);
        this.save(sysConfigType);
    }

    @Override
    public void del(SysConfigTypeRequest sysConfigTypeRequest) {
        SysConfigType dbSysConfigType = this.querySysConfigType(sysConfigTypeRequest);

        // 不能删除系统参数
        if (YesOrNotEnum.Y.getCode().equals(dbSysConfigType.getSysFlag())) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
        }

        this.removeById(dbSysConfigType);
    }

    @Override
    public void edit(SysConfigTypeRequest sysConfigTypeRequest) {
        SysConfigType dbSysConfigType = this.querySysConfigType(sysConfigTypeRequest);
        BeanUtil.copyProperties(sysConfigTypeRequest, dbSysConfigType);

        // 不允许修改系统参数、不允许修改系统参数类型编码
        dbSysConfigType.setSysFlag(null);
        dbSysConfigType.setConfigTypeCode(null);

        this.updateById(dbSysConfigType);
    }

    @Override
    public SysConfigType detail(SysConfigTypeRequest sysConfigTypeRequest) {
        return this.querySysConfigType(sysConfigTypeRequest);
    }

    @Override
    public List<SysConfigType> findList(SysConfigTypeRequest sysConfigTypeRequest) {
        LambdaQueryWrapper<SysConfigType> queryWrapper = this.createWrapper(sysConfigTypeRequest)
                .select(SysConfigType::getConfigTypeId, SysConfigType::getConfigTypeName,
                        SysConfigType::getConfigTypeCode, SysConfigType::getSysFlag,
                        SysConfigType::getConfigTypeSort, SysConfigType::getRemark
                );
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysConfigType> findPage(SysConfigTypeRequest sysConfigTypeRequest) {
        LambdaQueryWrapper<SysConfigType> queryWrapper = this.createWrapper(sysConfigTypeRequest)
                .select(SysConfigType::getConfigTypeId, SysConfigType::getConfigTypeName,
                        SysConfigType::getConfigTypeCode, SysConfigType::getSysFlag,
                        SysConfigType::getConfigTypeSort, SysConfigType::getRemark
                );
        Page<SysConfigType> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    private SysConfigType querySysConfigType(SysConfigTypeRequest sysConfigTypeRequest) {
        LambdaQueryWrapper<SysConfigType> queryWrapper = Wrappers.lambdaQuery(SysConfigType.class)
                .eq(SysConfigType::getConfigTypeId, sysConfigTypeRequest.getConfigTypeId());
        SysConfigType dbSysConfigType = this.getOne(queryWrapper, false);
        if (dbSysConfigType == null) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_TYPE_NOT_EXIST, "id: " + sysConfigTypeRequest.getConfigTypeId());
        }
        return dbSysConfigType;
    }

    /**
     * 创建查询Wrapper
     */
    private LambdaQueryWrapper<SysConfigType> createWrapper(SysConfigTypeRequest sysConfigTypeRequest) {
        return Wrappers.lambdaQuery(SysConfigType.class)
                .eq(StrUtil.isNotBlank(sysConfigTypeRequest.getConfigTypeName()), SysConfigType::getConfigTypeName, sysConfigTypeRequest.getConfigTypeName())
                .eq(StrUtil.isNotBlank(sysConfigTypeRequest.getConfigTypeCode()), SysConfigType::getConfigTypeCode, sysConfigTypeRequest.getConfigTypeCode())
                .eq(StrUtil.isNotBlank(sysConfigTypeRequest.getSysFlag()), SysConfigType::getSysFlag, sysConfigTypeRequest.getSysFlag())
                .orderByAsc(SysConfigType::getConfigTypeSort);
    }

}
