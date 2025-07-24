package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 用户创建/修改请求参数
 *
 * @author: benben
 * @time: 2025/7/2 下午4:57
 */
@Data
public class UserSaveReqVO {

    /**
     * 主键
     */
    @Schema(description = "用户ID", example = "10")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "笨笨")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号", example = "benben", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     * <p>加密方式：MD5+盐</p>
     */
    @Schema(description = "用户密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 头像
     * <p>头像存储路径</p>
     */
    @Schema(description = "用户头像", example = "https://baidu.com/xxx.png")
    private String avatar;

    /**
     * 性别
     * <p>性别枚举：{@link cn.ibenbeni.bens.rule.enums.SexEnum}</p>
     */
    @Schema(description = "用户性别", example = "M")
    private String sex;

    /**
     * 邮箱
     */
    @Schema(description = "用户邮箱", example = "ibenbeni@163.com", format = "email")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "用户号码", example = "1234569871478")
    private String phone;

    /**
     * 用户的排序
     */
    @Schema(description = "用户显示排序", example = "0")
    private BigDecimal userSort;

}
