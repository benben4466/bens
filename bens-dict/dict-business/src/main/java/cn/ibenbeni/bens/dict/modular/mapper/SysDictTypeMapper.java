package cn.ibenbeni.bens.dict.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.dict.modular.entity.SysDictTypeDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypePageReq;

/**
 * 字典类型表Mapper接口
 *
 * @author: benben
 * @time: 2025/6/13 下午11:30
 */
public interface SysDictTypeMapper extends BaseMapperX<SysDictTypeDO> {

    default SysDictTypeDO selectByName(String dictTypeName) {
        return selectOne(SysDictTypeDO::getDictTypeName, dictTypeName);
    }

    default SysDictTypeDO selectByCode(String dictTypeCode) {
        return selectOne(SysDictTypeDO::getDictTypeCode, dictTypeCode);
    }

    default PageResult<SysDictTypeDO> selectPage(DictTypePageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysDictTypeDO>()
                .likeIfPresent(SysDictTypeDO::getDictTypeName, req.getDictTypeName())
                .likeIfPresent(SysDictTypeDO::getDictTypeCode, req.getDictTypeCode())
                .eqIfPresent(SysDictTypeDO::getStatusFlag, req.getStatusFlag())
                .betweenIfPresent(SysDictTypeDO::getCreateTime, req.getCreateTime())
                .orderByDesc(SysDictTypeDO::getDictTypeSort)
        );
    }

}
