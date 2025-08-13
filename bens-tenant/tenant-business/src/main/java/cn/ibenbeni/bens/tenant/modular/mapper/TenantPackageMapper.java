package cn.ibenbeni.bens.tenant.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.tenant.modular.entity.TenantPackageDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackagePageReq;

import java.util.List;

/**
 * 租户套餐Mapper接口
 *
 * @author: benben
 * @time: 2025/6/30 下午5:23
 */
public interface TenantPackageMapper extends BaseMapperX<TenantPackageDO> {

    default TenantPackageDO selectByPackageName(String packageName) {
        return selectOne(TenantPackageDO::getPackageName, packageName);
    }

    default List<TenantPackageDO> selectListByStatusFlag(Integer statusFlag) {
        return selectList(TenantPackageDO::getStatusFlag, statusFlag);
    }

    default PageResult<TenantPackageDO> selectPage(TenantPackagePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<TenantPackageDO>()
                .likeIfPresent(TenantPackageDO::getPackageName, pageReq.getPackageName())
                .likeIfPresent(TenantPackageDO::getRemark, pageReq.getRemark())
                .eqIfPresent(TenantPackageDO::getStatusFlag, pageReq.getStatusFlag())
                .betweenIfPresent(TenantPackageDO::getCreateTime, pageReq.getCreateTime())
                .orderByDesc(TenantPackageDO::getPackageId)
        );
    }

}
