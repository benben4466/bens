package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建用户请求
 *
 * @author: benben
 * @time: 2025/7/2 上午10:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 是否是超级管理员
     * <p>Y-是，N-否</p>
     */
    private String superAdminFlag;

}
