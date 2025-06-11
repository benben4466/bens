package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 角色绑定权限的请求
 *
 * @author: benben
 * @time: 2025/6/9 下午10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleBindPermissionRequest extends BaseRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空", groups = {roleBindPermission.class, detail.class})
    private Long roleId;

    /**
     * 节点ID
     * <p>可以是菜单ID和按钮ID</p>
     */
    private Long nodeId;

    /**
     * 节点类型
     * <p>1-应用，2-菜单，3-功能，-1-所有权限</p>
     * <p>本程序无应用，因此2-菜单，3-功能，-1-所有权限</p>
     */
    @NotNull(message = "节点类型不能为空", groups = {roleBindPermission.class})
    private Integer permissionNodeType;

    /**
     * 是否选中
     */
    @NotNull(message = "是否选中不能为空", groups = {roleBindPermission.class})
    private Boolean checked;

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 角色绑定权限
     */
    public interface roleBindPermission {
    }

    // endregion

}
