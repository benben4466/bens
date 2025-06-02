package cn.ibenbeni.bens.sys.modular.user.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 用户角色关联封装类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleRequest extends BaseRequest {

    /**
     * 用户角色关联对象ID
     */
    private Long userRoleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色ID集合
     * <p>用在绑定用户角色</p>
     */
    private Set<Long> roleIdList;

}
