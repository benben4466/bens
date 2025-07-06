package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户更新密码请求参数
 *
 * @author: benben
 * @time: 2025/7/6 下午2:40
 */
@Data
public class UserUpdatePasswordReqVO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    /**
     * 新密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String newPassword;

}
