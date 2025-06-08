package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色数据范围封装类
 *
 * @author: benben
 * @time: 2025/6/8 下午2:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDataScopeRequest extends BaseRequest {

    /**
     * 主键
     */
    private Long roleDataScopeId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 组织ID
     */
    private Long organizationId;

}
