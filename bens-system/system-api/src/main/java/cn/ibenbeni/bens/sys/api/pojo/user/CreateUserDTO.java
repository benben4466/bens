package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

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
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     * <p>加密方式：MD5+盐</p>
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 头像
     * <p>头像存储路径</p>
     */
    private String avatar;

    /**
     * 性别
     * <p>性别枚举：{@link cn.ibenbeni.bens.rule.enums.SexEnum}</p>
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户的排序
     */
    private BigDecimal userSort;

}
