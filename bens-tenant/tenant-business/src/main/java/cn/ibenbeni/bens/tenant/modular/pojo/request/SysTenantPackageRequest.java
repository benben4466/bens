package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.sys.api.pojo.role.RoleBindPermissionItem;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 租户套餐请求参数
 *
 * @author: benben
 * @time: 2025/6/30 下午5:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysTenantPackageRequest extends BaseRequest {

    /**
     * 租户套餐编号
     */
    @NotNull(message = "套餐编号不能为空", groups = {edit.class, detail.class, delete.class})
    private Long packageId;

    /**
     * 租户套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = {add.class, edit.class})
    private String packageName;

    /**
     * 租户套餐关联的菜单及菜单功能
     * <p>菜单下边是菜单功能</p>
     * <p>此处用不到checked属性</p>
     */
    @NotNull(message = "关联的菜单编号不能为空", groups = {add.class, edit.class})
    private List<RoleBindPermissionItem> permissionList;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @NotNull(message = "状态不能为空", groups = {add.class, edit.class})
    private Integer statusFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户套餐ID集合
     * <p>批量操作使用</p>
     */
    @NotEmpty(message = "套餐编号集合不能为空", groups = {batchDelete.class})
    private Set<Long> packageIdList;

}
