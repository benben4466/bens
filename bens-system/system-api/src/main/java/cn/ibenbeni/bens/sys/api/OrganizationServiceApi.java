package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.org.HrOrganizationDTO;

import java.util.Collection;
import java.util.List;

/**
 * 组织机构信息的API
 *
 * @author benben
 */
public interface OrganizationServiceApi {

    /**
     * 获取组织机构的名称，通过组织机构ID
     *
     * @param orgId 组织机构ID
     * @return 组织机构名称
     */
    String getOrgNameById(Long orgId);

    /**
     * 通过组织机构ID获取组织机构信息
     *
     * @param orgId 组织机构ID
     * @return 组织机构信息
     */
    HrOrganizationDTO getOrgInfo(Long orgId);

    /**
     * 获取所有的组织机构信息
     *
     * @param orgIdList 组织机构ID集合
     * @return 获取所有的组织机构信息
     */
    List<HrOrganizationDTO> getOrgNameList(Collection<Long> orgIdList);

}
