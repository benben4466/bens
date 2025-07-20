package cn.ibenbeni.bens.sys.modular.user.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 绑定角色请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleBindReq {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
