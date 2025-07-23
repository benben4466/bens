package cn.ibenbeni.bens.sys.modular.org.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgListReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgPageReq;

import java.util.Collection;
import java.util.List;

/**
 * 组织机构信息Mapper接口
 *
 * @author benben
 * @date 2025/5/27  下午9:06
 */
public interface HrOrganizationMapper extends BaseMapperX<HrOrganizationDO> {

    /**
     * 根据组织名称查询
     */
    default HrOrganizationDO selectByOrgName(String orgName) {
        return selectOne(HrOrganizationDO::getOrgName, orgName);
    }

    /**
     * 根据组织编码查询
     */
    default HrOrganizationDO selectByOrgCode(String orgCode) {
        return selectOne(HrOrganizationDO::getOrgCode, orgCode);
    }

    /**
     * 获取子组织数量
     * <p>直接下一级组织数量</p>
     *
     * @param orgId 组织ID
     */
    default Long getChildOrgCount(Long orgId) {
        return selectCount(HrOrganizationDO::getOrgParentId, orgId);
    }

    /**
     * 根据父组织ID查询子组织信息列表
     * <p>直接子组织信息列表</p>
     */
    default List<HrOrganizationDO> selectChildList(Collection<Long> orgIds) {
        return selectList(HrOrganizationDO::getOrgParentId, orgIds);
    }

    /**
     * 根据查询条件获取组织列表
     */
    default List<HrOrganizationDO> selectList(OrgListReq reqVO) {
        return selectList(new LambdaQueryWrapperX<HrOrganizationDO>()
                .eqIfPresent(HrOrganizationDO::getOrgParentId, reqVO.getOrgParentId())
                .likeIfPresent(HrOrganizationDO::getOrgName, reqVO.getOrgName())
                .eqIfPresent(HrOrganizationDO::getStatusFlag, reqVO.getStatusFlag())
                .orderByDesc(HrOrganizationDO::getOrgSort)
                .orderByDesc(HrOrganizationDO::getCreateTime)
        );
    }

    /**
     * 分页查询组织列表
     */
    default PageResult<HrOrganizationDO> selectPage(OrgPageReq reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HrOrganizationDO>()
                .likeIfPresent(HrOrganizationDO::getOrgName, reqVO.getOrgName())
                .likeIfPresent(HrOrganizationDO::getOrgCode, reqVO.getOrgCode())
                .eqIfPresent(HrOrganizationDO::getStatusFlag, reqVO.getStatusFlag())
                .orderByDesc(HrOrganizationDO::getOrgSort)
                .orderByDesc(HrOrganizationDO::getCreateTime));
    }

}
