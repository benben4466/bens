package cn.ibenbeni.bens.sys.modular.role.pojo.response;

import lombok.Data;

/**
 * 角色绑定权限界面的响应封装
 *
 * @author: benben
 * @time: 2025/6/9 下午10:04
 */
@Data
public class RoleBindPermissionResponse {

    /**
     * 是否选择
     * <p>已拥有的是true</p>
     */
    private Boolean checked;

    /**
     * 节点类型
     * <p>1-应用，2-菜单，3-功能，-1-所有权限</p>
     * <p>本程序无应用，因此2-菜单，3-功能，-1-所有权限</p>
     */
    private Integer permissionNodeType = -1;

}
