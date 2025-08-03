package cn.ibenbeni.bens.dict.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.exception.DictException;
import cn.ibenbeni.bens.dict.api.exception.enums.DictExceptionEnum;
import cn.ibenbeni.bens.dict.modular.entity.SysDictDO;
import cn.ibenbeni.bens.dict.modular.entity.SysDictTypeDO;
import cn.ibenbeni.bens.dict.modular.mapper.SysDictMapper;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictPageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictSaveReq;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典服务实现类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:56
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictDO> implements SysDictService {

    // region 属性

    @Resource
    private SysDictMapper dictMapper;

    @Lazy
    @Resource
    private SysDictTypeService dictTypeService;

    // endregion

    // region 公共方法

    @Override
    public Long createDict(DictSaveReq req) {
        // 校验字典类型是否存在
        validateDictTypeExists(req.getDictTypeCode());
        // 校验字典值是否唯一
        validateDictValueUnique(null, req.getDictTypeCode(), req.getDictValue());

        SysDictDO dict = BeanUtil.toBean(req, SysDictDO.class);
        save(dict);
        return dict.getDictId();
    }

    @Override
    public void deleteDict(Long dictId) {
        // 校验字典是否存在
        validateDictExists(dictId);

        removeById(dictId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteDict(Set<Long> dictIdSet) {
        removeBatchByIds(dictIdSet);
    }

    @Override
    public void updateDict(DictSaveReq req) {
        // 校验字典是否存在
        validateDictExists(req.getDictId());
        // 校验字典类型是否存在
        validateDictTypeExists(req.getDictTypeCode());
        // 校验字典值是否唯一
        validateDictValueUnique(req.getDictId(), req.getDictTypeCode(), req.getDictValue());

        SysDictDO dict = BeanUtil.toBean(req, SysDictDO.class);
        updateById(dict);
    }

    @Override
    public SysDictDO getDict(Long dictId) {
        return getById(dictId);
    }

    @Override
    public SysDictDO getDict(String dictTypeCode, String dictValue) {
        return dictMapper.selectByValueAndDictTypeCode(dictValue, dictTypeCode);
    }

    @Override
    public long getDictCountByDictTypeCode(String dictTypeCode) {
        return dictMapper.selectCountByDictTypeCode(dictTypeCode);
    }

    @Override
    public List<SysDictDO> listByStatusAndDictTypeCode(Integer dictStatusFlag, String dictTypeCode) {
        return dictMapper.selectListByStatusFlagAndDictTypeCode(dictStatusFlag, dictTypeCode);
    }

    @Override
    public List<SysDictDO> listByDictTypeCode(String dictTypeCode) {
        return dictMapper.selectListByDictTypeCode(dictTypeCode);
    }

    @Override
    public PageResult<SysDictDO> getDictPage(DictPageReq req) {
        return dictMapper.selectPage(req);
    }

    @Override
    public void validateDictList(String dictTypeCode, Set<String> dictValueSet) {
        if (ObjectUtil.hasNull(dictTypeCode, dictValueSet)) {
            return;
        }

        Map<String, SysDictDO> dictMap = CollectionUtils.convertMap(
                dictMapper.selectListByValueAndDictTypeCode(dictValueSet, dictTypeCode),
                SysDictDO::getDictValue
        );
        dictValueSet.forEach(value -> {
            SysDictDO dict = dictMap.get(value);
            if (dict == null) {
                throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED);
            }
            if (!StatusEnum.ENABLE.getCode().equals(dict.getStatusFlag())) {
                throw new DictException(DictExceptionEnum.DICT_NOT_ENABLE);
            }
        });
    }

    // endregion

    // region 私有方法

    /**
     * 校验字典类型是否存在
     *
     * @param dictTypeCode 字典类型编码
     */
    private void validateDictTypeExists(String dictTypeCode) {
        SysDictTypeDO dictType = dictTypeService.getDictType(dictTypeCode);
        if (dictType == null) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_EXISTED);
        }
        // 校验字典类型状态
        if (!StatusEnum.ENABLE.getCode().equals(dictType.getStatusFlag())) {
            throw new DictException(DictExceptionEnum.DICT_TYPE_NOT_ENABLE);
        }
    }

    private void validateDictExists(Long dictId) {
        SysDictDO dict = getById(dictId);
        if (dict == null) {
            throw new DictException(DictExceptionEnum.DICT_NOT_EXISTED);
        }
    }

    /**
     * 校验字典值是否唯一
     * <p>唯一条件：在同一字典类型编码下，字典值唯一</p>
     *
     * @param dictId       字典ID
     * @param dictTypeCode 字典类型编码
     * @param dictValue    字典值
     */
    private void validateDictValueUnique(Long dictId, String dictTypeCode, String dictValue) {
        SysDictDO dict = dictMapper.selectByValueAndDictTypeCode(dictValue, dictTypeCode);
        if (dict == null) {
            return;
        }

        if (dictId == null) {
            throw new DictException(DictExceptionEnum.DICT_VALUE_DUPLICATE);
        }
        if (ObjectUtil.notEqual(dictId, dict.getDictId())) {
            throw new DictException(DictExceptionEnum.DICT_VALUE_DUPLICATE);
        }
    }

    // endregion

}
