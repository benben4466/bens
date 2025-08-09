package cn.ibenbeni.bens.sys.api;

import java.util.List;

/**
 * 用户组织服务接口
 */
public interface UserOrgServiceApi {

    /**
     * 获取用户所有组织机构ID
     *
     * @param userId 用户ID
     * @return 组织机构ID集合
     */
    List<Long> listOrgByUserId(Long userId);

}
