package cn.ibenbeni.bens.sys.modular.role.action;

import cn.ibenbeni.bens.sys.modular.role.enums.PermissionNodeTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;

import java.util.Set;

/**
 * 角色绑定权限操作的接口
 *
 * @author: benben
 * @time: 2025/6/10 上午10:18
 */
public interface RoleAssignOperateAction {

    /**
     * 获取节点的类型
     */
    PermissionNodeTypeEnum getNodeType();

    /**
     * 执行角色绑定权限的过程，根据不同的点击类型，执行不同的操作过程
     *
     * @param roleBindPermissionRequest    角色绑定权限的参数
     * @param roleLimitMenuIdsAndOptionIds 角色所能承受的绑定范围
     */
    void doOperateAction(RoleBindPermissionRequest roleBindPermissionRequest, Set<Long> roleLimitMenuIdsAndOptionIds);

}
