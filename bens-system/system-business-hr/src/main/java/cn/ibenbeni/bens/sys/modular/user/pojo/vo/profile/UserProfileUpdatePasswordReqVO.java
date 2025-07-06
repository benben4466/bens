package cn.ibenbeni.bens.sys.modular.user.pojo.vo.profile;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新密码请求参数【个人中心】
 *
 * @author: benben
 * @time: 2025/7/3 下午9:15
 */
@Data
public class UserProfileUpdatePasswordReqVO {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String newPassword;

}
