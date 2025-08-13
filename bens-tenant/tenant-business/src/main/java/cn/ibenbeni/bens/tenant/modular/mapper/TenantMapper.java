package cn.ibenbeni.bens.tenant.modular.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.tenant.modular.entity.TenantDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPageReq;

import java.util.List;

/**
 * 租户Mapper接口
 *
 * @author: benben
 * @time: 2025/7/1 上午11:05
 */
public interface TenantMapper extends BaseMapperX<TenantDO> {

    default TenantDO getTenantByName(String tenantName) {
        return selectOne(TenantDO::getTenantName, tenantName);
    }

    default TenantDO getTenantByWebsite(String website) {
        return selectOne(TenantDO::getTenantWebsite, website);
    }

    default Long selectCountByPackageId(Long packageId) {
        return selectCount(TenantDO::getTenantPackageId, packageId);
    }

    default List<TenantDO> selectListByPackageId(Long packageId) {
        return selectList(TenantDO::getTenantId, packageId);
    }

    default List<TenantDO> selectListByStatusFlag(Integer statusFlag) {
        return selectList(TenantDO::getStatusFlag, statusFlag);
    }

    default PageResult<TenantDO> selectPage(TenantPageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<TenantDO>()
                .likeIfPresent(TenantDO::getTenantName, pageReq.getTenantName())
                .likeIfPresent(TenantDO::getContactName, pageReq.getContactName())
                .likeIfPresent(TenantDO::getContactMobile, pageReq.getContactMobile())
                .eqIfPresent(TenantDO::getStatusFlag, pageReq.getStatusFlag())
                .betweenIfPresent(TenantDO::getCreateTime, pageReq.getCreateTime())
                .orderByDesc(TenantDO::getTenantId)
        );
    }

}
