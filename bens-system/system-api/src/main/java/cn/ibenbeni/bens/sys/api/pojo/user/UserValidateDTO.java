package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用在用户登录校验结果的包装
 *
 * @author benben
 * @date 2025/4/19  下午1:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserValidateDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 加密后的密码，存在sys_user表的password字段
     */
    private String userPasswordHexed;

    /**
     * 密码盐，存在sys_user表的password_salt字段
     */
    private String userPasswordSalt;

    /**
     * 用户状态，状态在UserStatusEnum维护
     */
    private Integer userStatus;

    /**
     * 账号
     */
    private String account;

}