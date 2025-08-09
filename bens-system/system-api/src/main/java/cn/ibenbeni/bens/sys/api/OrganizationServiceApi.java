package cn.ibenbeni.bens.sys.api;

import java.util.Set;

/**
 * 组织机构信息的API
 *
 * @author benben
 */
public interface OrganizationServiceApi {

    /**
     * 获取部门下的所有子部门ID集合
     *
     * @param deptIdSet 部门ID集合
     * @return 子部门ID集合
     */
    Set<Long> listChildDeptId(Set<Long> deptIdSet);

}
