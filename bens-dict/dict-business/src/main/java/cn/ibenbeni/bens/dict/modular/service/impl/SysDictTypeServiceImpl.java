package cn.ibenbeni.bens.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.exception.DictException;
import cn.ibenbeni.bens.dict.api.exception.enums.DictExceptionEnum;
import cn.ibenbeni.bens.dict.modular.entity.SysDictTypeDO;
import cn.ibenbeni.bens.dict.modular.mapper.SysDictTypeMapper;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypePageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeSaveReq;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 字典类型表服务实现类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:33
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictTypeDO> implements SysDictTypeService {

    // region 属性

    @Resource
    private SysDictTypeMapper dictTypeMapper;

    @Resource
    private SysDictService dictService;

    // endregion

    // region 公共方法

    @Override
    public Long createDictType(DictTypeSaveReq req) {
        // 校验字典类型名称和编码唯一性
        validateDictTypeNameUnique(null, req.getDictTypeName());
        validateDictTypeCodeUnique(null, req.getDictTypeCode());

        SysDictTypeDO dictType = BeanUtil.toBean(req, SysDictTypeDO.class);
        save(dictType);
        return dictType.getDictTypeId();
    }

    @Override
    public void deleteDictType(Long dictTypeId) {
        // 校验字典类型是否存在
        SysDictTypeDO dictType = validateDictTypeExists(dictTypeId);
        long dictCount = dictService.getDictCountByDictTypeCode(dictType.getDictTypeCode());
        if (dictCount > 0) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_HAS_CHILDREN);
        }
        removeById(dictTypeId);
    }

    @Override
    public void deleteDictType(Set<Long> dictTypeIdSet) {
        if (CollUtil.isEmpty(dictTypeIdSet)) {
            return;
        }
        List<SysDictTypeDO> dictTypeList = listByIds(dictTypeIdSet);
        dictTypeList.forEach(dictType -> {
            long dictCount = dictService.getDictCountByDictTypeCode(dictType.getDictTypeCode());
            if (dictCount > 0) {
                throw new DictException(DictExceptionEnum.DICT_TYPE_HAS_CHILDREN);
            }
        });

        removeByIds(dictTypeIdSet);
    }

    @Override
    public void updateDictType(DictTypeSaveReq req) {
        // 校验字典类型是否存在
        SysDictTypeDO dictType = validateDictTypeExists(req.getDictTypeId());
        // 校验字典类型名称和编码唯一性
        validateDictTypeNameUnique(req.getDictTypeId(), req.getDictTypeName());
        validateDictTypeCodeUnique(req.getDictTypeId(), req.getDictTypeCode());

        BeanUtil.copyProperties(req, dictType);
        updateById(dictType);
    }

    @Override
    public SysDictTypeDO getDictType(Long dictTypeId) {
        return getById(dictTypeId);
    }

    @Override
    public SysDictTypeDO getDictType(String dictTypeCode) {
        return dictTypeMapper.selectByCode(dictTypeCode);
    }

    @Override
    public PageResult<SysDictTypeDO> getDictTypePage(DictTypePageReq req) {
        return dictTypeMapper.selectPage(req);
    }

    @Override
    public List<SysDictTypeDO> getDictTypeList() {
        return list();
    }

    // endregion

    // region 私有方法

    /**
     * 校验字典类型名称唯一
     *
     * @param dictTypeId   字典类型ID
     * @param dictTypeName 字典类型名称
     */
    void validateDictTypeNameUnique(Long dictTypeId, String dictTypeName) {
        SysDictTypeDO dictType = dictTypeMapper.selectByName(dictTypeName);
        if (dictType == null) {
            return;
        }

        if (dictTypeId == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NAME_DUPLICATE, dictTypeName);
        }
        if (ObjectUtil.notEqual(dictTypeId, dictType.getDictTypeId())) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NAME_DUPLICATE, dictTypeName);
        }
    }

    void validateDictTypeCodeUnique(Long dictTypeId, String dictTypeCode) {
        SysDictTypeDO dictType = dictTypeMapper.selectByCode(dictTypeCode);
        if (dictType == null) {
            return;
        }

        if (dictTypeId == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_CODE_DUPLICATE, dictTypeCode);
        }
        if (ObjectUtil.notEqual(dictTypeId, dictType.getDictTypeId())) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_CODE_DUPLICATE, dictTypeCode);
        }
    }

    SysDictTypeDO validateDictTypeExists(Long dictTypeId) {
        SysDictTypeDO dictType = getById(dictTypeId);
        if (dictType == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED);
        }
        return dictType;
    }

    // endregion

}
