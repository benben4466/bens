package cn.ibenbeni.bens.sys.api;

import java.util.List;

/**
 * 角色信息相关的Api
 *
 * @author benben
 */
public interface SysRoleServiceApi {

    /**
     * 获取默认角色ID
     */
    Long getDefaultRoleId();

    /**
     * 获取角色对应的菜单功能编码集合
     *
     * @param roleCode 角色编码
     */
    List<String> getRoleMenuOptionsByRoleId(String roleCode);

}
