package cn.ibenbeni.bens.sys.modular.role.action;

import cn.ibenbeni.bens.sys.modular.role.enums.PermissionNodeTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;

/**
 * 角色绑定权限限制的接口
 *
 * @author: benben
 * @time: 2025/6/10 上午10:04
 */
public interface RoleBindLimitAction {

    /**
     * 获取操作的类型，有4种节点类型
     */
    PermissionNodeTypeEnum getRoleBindLimitNodeType();

    /**
     * 进行角色绑定权限限制的过程，执行绑定的操作
     */
    void doRoleBindLimitAction(RoleBindPermissionRequest roleBindPermissionRequest);

}
