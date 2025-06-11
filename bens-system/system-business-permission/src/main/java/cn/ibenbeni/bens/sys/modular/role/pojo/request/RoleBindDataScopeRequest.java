package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色绑定数据权限的请求
 *
 * @author: benben
 * @time: 2025/6/8 下午2:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleBindDataScopeRequest extends BaseRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空", groups = {detail.class, roleBindDataScope.class})
    private Long roleId;

    /**
     * 数据范围类型
     * <p>10-仅本人数据，20-本部门数据，30-本部门及以下数据，31-本公司及以下数据，40-指定部门数据，50-全部数据</p>
     */
    @NotNull(message = "数据范围类型不能为空", groups = {roleBindDataScope.class})
    private Integer dataScopeType;

    /**
     * 用户拥有的指定部门的组织机构信息ID集合
     */
    private List<Long> orgIdList;

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 角色绑定数据范围
     */
    public interface roleBindDataScope {
    }

    // endregion

}
