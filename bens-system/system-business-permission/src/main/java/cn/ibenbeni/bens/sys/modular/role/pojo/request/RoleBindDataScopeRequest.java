package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Long roleId;

    /**
     * 数据范围类型
     * <p>10-仅本人数据，20-本部门数据，30-本部门及以下数据，31-本公司及以下数据，40-指定部门数据，50-全部数据</p>
     */
    private Integer dataScopeType;

    /**
     * 用户拥有的指定部门的组织机构信息ID集合
     */
    private List<Long> orgIdList;

}
