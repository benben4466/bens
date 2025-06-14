package cn.ibenbeni.bens.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.exception.DictException;
import cn.ibenbeni.bens.dict.api.exception.enums.DictExceptionEnum;
import cn.ibenbeni.bens.dict.modular.entity.SysDict;
import cn.ibenbeni.bens.dict.modular.entity.SysDictType;
import cn.ibenbeni.bens.dict.modular.mapper.SysDictMapper;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictRequest;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import cn.ibenbeni.bens.rule.pojo.dict.SimpleDict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典服务实现类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:56
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Resource
    private SysDictTypeService sysDictTypeService;

    @Override
    public void add(DictRequest dictRequest) {
        // 校验参数合法性
        this.validateRepeat(dictRequest, false);

        SysDict sysDict = BeanUtil.toBean(dictRequest, SysDict.class);
        this.save(sysDict);
    }

    @Override
    public void del(DictRequest dictRequest) {
        this.removeById(dictRequest.getDictId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(DictRequest dictRequest) {
        this.removeBatchByIds(dictRequest.getDictIdList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delByDictTypeId(Long dictTypeId) {
        LambdaQueryWrapper<SysDict> removeWrapper = Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictTypeId, dictTypeId);
        this.remove(removeWrapper);
    }

    @Override
    public void edit(DictRequest dictRequest) {
        // 校验参数合法性
        this.validateRepeat(dictRequest, true);

        SysDict dbSysDict = this.querySysDict(dictRequest.getDictId());
        BeanUtil.copyProperties(dictRequest, dbSysDict);
        this.updateById(dbSysDict);
    }

    @Override
    public SysDict detail(DictRequest dictRequest) {
        SysDict dbSysDict = this.querySysDict(dictRequest.getDictId());
        if (dbSysDict.getDictTypeId() != null) {
            SysDictType dbSysDictType = sysDictTypeService.getById(dbSysDict.getDictTypeId());
            dbSysDict.setDictTypeName(dbSysDictType.getDictTypeName());
        }
        return dbSysDict;
    }

    @Override
    public List<SysDict> findList(DictRequest dictRequest) {
        LambdaQueryWrapper<SysDict> queryWrapper = this.createWrapper(dictRequest, false)
                .select(SysDict::getDictName, SysDict::getDictValue, SysDict::getDictSort, SysDict::getDictId);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysDict> findPage(DictRequest dictRequest) {
        LambdaQueryWrapper<SysDict> queryWrapper = this.createWrapper(dictRequest, true)
                .select(SysDict::getDictName, SysDict::getDictValue, SysDict::getDictSort, SysDict::getDictId);

        Page<SysDict> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode) {
        Long dictTypeId = sysDictTypeService.getDictTypeIdByDictTypeCode(dictTypeCode);
        if (dictTypeId == null) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<SysDict> queryWrapper = Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictTypeId, dictTypeId)
                .select(SysDict::getDictId, SysDict::getDictName, SysDict::getDictValue);
        List<SysDict> sysDicts = this.list(queryWrapper);
        if (CollUtil.isEmpty(sysDicts)) {
            return new ArrayList<>();
        }

        return sysDicts.stream()
                .map(sysDict -> {
                    SimpleDict simpleDict = new SimpleDict();
                    simpleDict.setId(sysDict.getDictId());
                    simpleDict.setName(sysDict.getDictName());
                    simpleDict.setValue(sysDict.getDictValue());
                    return simpleDict;
                })
                .collect(Collectors.toList());
    }

    /**
     * 检查新增的字典名称是否重复
     *
     * @param dictRequest 请求
     * @param editFlag    是否是编辑
     */
    private void validateRepeat(DictRequest dictRequest, boolean editFlag) {
        // 检查同字典类型下，字典名称是否重复
        LambdaQueryWrapper<SysDict> queryWrapper = Wrappers.lambdaQuery(SysDict.class)
                .eq(SysDict::getDictTypeId, dictRequest.getDictTypeId())
                .eq(SysDict::getDictName, dictRequest.getDictName());
        if (editFlag) {
            queryWrapper.ne(SysDict::getDictId, dictRequest.getDictId());
        }
        long dictNameCount = this.count(queryWrapper);
        if (dictNameCount > 0) {
            throw new DictException(DictExceptionEnum.DICT_NAME_REPEAT, dictRequest.getDictTypeId(), dictRequest.getDictName());
        }
    }

    private SysDict querySysDict(Long dictId) {
        SysDict dbSysDict = this.getById(dictId);
        if (dbSysDict == null) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED, dictId);
        }
        return dbSysDict;
    }

    /**
     * 创建Wrapper
     */
    private LambdaQueryWrapper<SysDict> createWrapper(DictRequest dictRequest, boolean pageFlag) {
        LambdaQueryWrapper<SysDict> queryWrapper = Wrappers.lambdaQuery(SysDict.class)
                .eq(!pageFlag, SysDict::getDictId, dictRequest.getDictId())
                .eq(ObjectUtil.isNotEmpty(dictRequest.getDictTypeId()), SysDict::getDictTypeId, dictRequest.getDictTypeId())
                .orderByAsc(SysDict::getDictSort);
        if (StrUtil.isNotBlank(dictRequest.getDictTypeCode())) {
            Long dictTypeId = sysDictTypeService.getDictTypeIdByDictTypeCode(dictRequest.getDictTypeCode());
            if (dictTypeId != null) {
                queryWrapper.eq(SysDict::getDictTypeId, dictTypeId);
            } else {
                // 字典类型不存在，则查询根本不存在的字典类型ID
                queryWrapper.eq(SysDict::getDictTypeId, -1);
            }
        }
        return queryWrapper;
    }

}
