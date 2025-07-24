package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleDTO {

    /**
     * 主键
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

}
