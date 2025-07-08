package cn.ibenbeni.bens.sys.modular.org.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.OrganizationServiceApi;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrgPageReqVO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrganizationSaveReqVO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrgListReqVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.*;

/**
 * 组织机构信息服务类
 *
 * @author benben
 */
public interface HrOrganizationService extends IService<HrOrganizationDO>, OrganizationServiceApi {

    /**
     * 创建组织机构
     *
     * @return 组织机构ID
     */
    Long createOrg(OrganizationSaveReqVO createReqVO);

    /**
     * 删除组织机构
     *
     * @param orgId 组织机构ID
     */
    void deleteOrg(Long orgId);

    /**
     * 更新组织机构
     */
    void updateOrg(OrganizationSaveReqVO updateReqVO);

    /**
     * 获取组织机构信息
     *
     * @param orgId 组织机构ID
     * @return 组织机构信息
     */
    HrOrganizationDO getOrg(Long orgId);

    /**
     * 获取组织机构信息列表
     *
     * @param orgIds 组织机构ID列表
     * @return 组织机构信息列表
     */
    List<HrOrganizationDO> getOrgList(Collection<Long> orgIds);

    /**
     * 根据条件查询组织信息列表
     */
    List<HrOrganizationDO> getOrgList(OrgListReqVO reqVO);

    /**
     * 获取指定组织ID的所有子部门
     *
     * @param ids 组织ID列表
     */
    List<HrOrganizationDO> getChildOrgList(Collection<Long> ids);

    /**
     * 获取指定组织ID的获得所有子部门ID
     * <p>数据来源: 缓存</p>
     *
     * @param orgId 组织ID
     */
    Set<Long> getChildOrgIdListFromCache(Long orgId);

    /**
     * 获取组织列表（分页）
     */
    PageResult<HrOrganizationDO> getOrgPage(OrgPageReqVO reqVO);

    /**
     * 校验组织是否有效
     * <p>组织无效情况：1.组织ID不存在；2.部门被禁用</p>
     *
     * @param orgIds 组织ID集合
     */
    void validateOrgList(Collection<Long> orgIds);

    /**
     * 获取指定ID的机构信息Map
     * <p>主要用于填充组织信息时获取信息</p>
     *
     * @param orgIds 机构ID集合
     */
    default Map<Long, HrOrganizationDO> getOrgMap(Collection<Long> orgIds) {
        List<HrOrganizationDO> orgList = this.getOrgList(orgIds);
        return CollectionUtils.convertMap(orgList, HrOrganizationDO::getOrgId);
    }

    /**
     * 获得指定组织下，所有子组织信息
     *
     * @param orgId 组织ID
     */
    default List<HrOrganizationDO> getChildOrgList(Long orgId) {
        return this.getChildOrgList(Collections.singleton(orgId));
    }

}
