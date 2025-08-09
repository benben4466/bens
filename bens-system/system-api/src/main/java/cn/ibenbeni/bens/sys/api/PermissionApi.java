package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.sys.api.pojo.permission.dto.DeptDataPermissionRespDTO;

import java.util.List;
import java.util.Set;

/**
 * 权限分配业务API
 *
 * @author: benben
 * @time: 2025/7/2 上午9:41
 */
public interface PermissionApi {

    /**
     * 校验当前用户是否有某个接口的权限
     *
     * @param token              用户登录Token
     * @param permissionCodeList 需要的权限编码
     * @throws AuthException 认证异常
     */
    void validatePermission(String token, List<String> permissionCodeList) throws AuthException;

    /**
     * 获取用户的权限编码集合
     *
     * @param userId 用户ID
     * @return 权限编码集合
     */
    Set<String> getUserPermissionCodeList(Long userId);

    /**
     * 获取登陆用户的部门数据权限
     *
     * @param userId 用户ID
     * @return 部门数据权限
     */
    DeptDataPermissionRespDTO getDeptDataPermission(Long userId);

}
