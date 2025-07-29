package cn.ibenbeni.bens.config.modular.mapper;

import cn.ibenbeni.bens.config.modular.entity.SysConfigTypeDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypePageReq;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;

/**
 * 参数配置类型Mapper接口
 *
 * @author: benben
 * @time: 2025/6/18 下午10:33
 */
public interface SysConfigTypeMapper extends BaseMapperX<SysConfigTypeDO> {

    default SysConfigTypeDO selectByCode(String configTypeCode) {
        return selectOne(SysConfigTypeDO::getConfigTypeCode, configTypeCode);
    }

    default PageResult<SysConfigTypeDO> selectPage(SysConfigTypePageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysConfigTypeDO>()
                .likeIfPresent(SysConfigTypeDO::getConfigTypeName, req.getConfigTypeName())
                .likeIfPresent(SysConfigTypeDO::getConfigTypeCode, req.getConfigTypeCode())
                .eqIfPresent(SysConfigTypeDO::getConfigType, req.getConfigType())
                .betweenIfPresent(SysConfigTypeDO::getCreateTime, req.getCreateTime())
                .orderByAsc(SysConfigTypeDO::getConfigTypeSort)
        );
    }

}
