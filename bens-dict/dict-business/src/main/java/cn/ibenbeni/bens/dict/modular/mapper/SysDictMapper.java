package cn.ibenbeni.bens.dict.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.dict.modular.entity.SysDictDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictPageReq;

import java.util.List;
import java.util.Set;

/**
 * 字典Mapper接口
 *
 * @author: benben
 * @time: 2025/6/13 下午11:54
 */
public interface SysDictMapper extends BaseMapperX<SysDictDO> {

    default long selectCountByDictTypeCode(String dictTypeCode) {
        return selectCount(SysDictDO::getDictTypeCode, dictTypeCode);
    }

    default SysDictDO selectByValueAndDictTypeCode(String dictValue, String dictTypeCode) {
        return selectOne(SysDictDO::getDictValue, dictValue, SysDictDO::getDictTypeCode, dictTypeCode);
    }

    default SysDictDO selectByCodeAndDictTypeCode(String dictCode, String dictTypeCode) {
        return selectOne(SysDictDO::getDictCode, dictCode, SysDictDO::getDictTypeCode, dictTypeCode);
    }

    default  List<SysDictDO> selectListByDictTypeCode(String dictTypeCode) {
        return selectList(SysDictDO::getDictTypeCode, dictTypeCode);
    }

    default List<SysDictDO> selectListByValueAndDictTypeCode(Set<String> dictValueSet, String dictTypeCode) {
        return selectList(new LambdaQueryWrapperX<SysDictDO>()
                .eq(SysDictDO::getDictTypeCode, dictTypeCode)
                .in(SysDictDO::getDictValue, dictValueSet)
        );
    }

    default List<SysDictDO> selectListByStatusFlagAndDictTypeCode(Integer statusFlag, String dictTypeCode) {
        return selectList(new LambdaQueryWrapperX<SysDictDO>()
                .eqIfPresent(SysDictDO::getStatusFlag, statusFlag)
                .eqIfPresent(SysDictDO::getDictTypeCode, dictTypeCode)
        );
    }

    default PageResult<SysDictDO> selectPage(DictPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysDictDO>()
                .eqIfPresent(SysDictDO::getDictTypeCode, req.getDictTypeCode())
                .eqIfPresent(SysDictDO::getDictCode, req.getDictCode())
                .eqIfPresent(SysDictDO::getStatusFlag, req.getStatusFlag())
        );
    }

}
