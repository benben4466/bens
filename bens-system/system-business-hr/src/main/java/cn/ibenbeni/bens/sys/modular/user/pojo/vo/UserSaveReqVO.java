package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户创建/修改请求参数
 *
 * @author: benben
 * @time: 2025/7/2 下午4:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSaveReqVO extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Long userId;

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
