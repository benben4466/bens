package cn.ibenbeni.bens.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.enums.DictTypeClassEnum;
import cn.ibenbeni.bens.dict.api.exception.DictException;
import cn.ibenbeni.bens.dict.api.exception.enums.DictExceptionEnum;
import cn.ibenbeni.bens.dict.modular.entity.SysDictType;
import cn.ibenbeni.bens.dict.modular.mapper.SysDictTypeMapper;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeRequest;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型表服务实现类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:33
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Lazy
    @Resource
    private SysDictService sysDictService;

    @Override
    public void add(DictTypeRequest dictTypeRequest) {
        // 校验请求是否合法
        this.validateRequest(dictTypeRequest, false);
        SysDictType sysDictType = BeanUtil.toBean(dictTypeRequest, SysDictType.class);
        this.save(sysDictType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(DictTypeRequest dictTypeRequest) {
        // 系统级字典只能管理员操作
        this.validateSystemTypeClassOperate(dictTypeRequest);

        // 删除字典类型及字典数据
        this.removeById(dictTypeRequest.getDictTypeId());
        this.sysDictService.delByDictTypeId(dictTypeRequest.getDictTypeId());
    }

    @Override
    public void edit(DictTypeRequest dictTypeRequest) {
        // 校验请求是否合法
        this.validateRequest(dictTypeRequest, true);

        SysDictType dbDictType = this.querySysDictType(dictTypeRequest.getDictTypeId());
        BeanUtil.copyProperties(dictTypeRequest, dbDictType);
        // 字典类型编码不能修改
        dbDictType.setDictTypeCode(null);

        this.updateById(dbDictType);
    }

    @Override
    public SysDictType detail(DictTypeRequest dictTypeRequest) {
        return this.querySysDictType(dictTypeRequest.getDictTypeId());
    }

    @Override
    public List<SysDictType> findList(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = this.createWrapper(dictTypeRequest)
                .select(SysDictType::getDictTypeName, SysDictType::getDictTypeId);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest) {
        LambdaQueryWrapper<SysDictType> queryWrapper = this.createWrapper(dictTypeRequest)
                .select(SysDictType::getDictTypeName, SysDictType::getDictTypeId);
        Page<SysDictType> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public Long getDictTypeIdByDictTypeCode(String dictTypeCode) {
        if (StrUtil.isEmpty(dictTypeCode)) {
            return null;
        }

        LambdaQueryWrapper<SysDictType> queryWrapper = Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictTypeCode, dictTypeCode)
                .select(SysDictType::getDictTypeId);
        SysDictType dbDictType = this.getOne(queryWrapper, false);
        return dbDictType != null ? dbDictType.getDictTypeId() : null;
    }

    /**
     * 校验请求是否合法
     *
     * @param dictTypeRequest 请求参数
     * @param editFlag        是否编辑
     */
    private void validateRequest(DictTypeRequest dictTypeRequest, boolean editFlag) {
        this.validateSystemTypeClassOperate(dictTypeRequest);
        this.validateRepeat(dictTypeRequest, editFlag);
    }

    /**
     * 校验dictTypeClass是否是系统字典，如果是系统字典只能超级管理员操作
     *
     * @param dictTypeRequest 请求参数
     */
    private void validateSystemTypeClassOperate(DictTypeRequest dictTypeRequest) {
        if (DictTypeClassEnum.BUSINESS_TYPE.getCode().equals(dictTypeRequest.getDictTypeClass())) {
            if (!LoginContext.me().getSuperAdminFlag()) {
                throw new DictException(DictExceptionEnum.SYSTEM_DICT_NOT_ALLOW_OPERATION);
            }
        }
    }

    /**
     * 校验字典类型名称和字典类型编码是否重复
     *
     * @param dictTypeRequest 请求参数
     * @param editFlag        是否编辑
     */
    private void validateRepeat(DictTypeRequest dictTypeRequest, boolean editFlag) {
        // 字典类型编码是否重复
        LambdaQueryWrapper<SysDictType> dictTypeCodeWrapper = Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictTypeCode, dictTypeRequest.getDictTypeCode());
        if (editFlag) {
            dictTypeCodeWrapper.ne(SysDictType::getDictTypeId, dictTypeRequest.getDictTypeId());
        }
        long dictTypeCodeCount = this.count(dictTypeCodeWrapper);
        if (dictTypeCodeCount > 0) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_CODE_REPEAT, dictTypeRequest.getDictTypeCode());
        }

        // 字典类型名称是否重复
        LambdaQueryWrapper<SysDictType> dictTypeNameWrapper = Wrappers.lambdaQuery(SysDictType.class).eq(SysDictType::getDictTypeName, dictTypeRequest.getDictTypeName());
        if (editFlag) {
            dictTypeNameWrapper.ne(SysDictType::getDictTypeId, dictTypeRequest.getDictTypeId());
        }
        long dictTypeNameCount = this.count(dictTypeNameWrapper);
        if (dictTypeNameCount > 0) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NAME_REPEAT, dictTypeRequest.getDictTypeName());
        }
    }

    private SysDictType querySysDictType(Long dictTypeId) {
        SysDictType sysDictType = this.getById(dictTypeId);
        if (ObjectUtil.isEmpty(sysDictType)) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED, dictTypeId);
        }
        return sysDictType;
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<SysDictType> createWrapper(DictTypeRequest dictTypeRequest) {
        return Wrappers.lambdaQuery(SysDictType.class)
                .orderByAsc(SysDictType::getDictTypeSort);
    }

}
