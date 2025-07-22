package cn.ibenbeni.bens.sys.modular.user.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 系统用户封装类
 *
 * @author benben
 * @date 2025/4/19  下午1:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Long userId;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    /**
     * 头像
     * <p>头像存储地址</p>
     */
    private String avatar;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别：M-男，F-女
     */
    @NotBlank(message = "性别：M-男，F-女不能为空")
    private String sex;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 是否是超级管理员：Y-是，N-否
     */
    @NotBlank(message = "是否是超级管理员：Y-是，N-否不能为空")
    private String superAdminFlag;

    /**
     * 状态：1-正常，2-冻结，3-临时冻结
     */
    @NotNull(message = "状态不能为空")
    private Integer statusFlag;

    /**
     * 用户的排序
     */
    private BigDecimal userSort = new BigDecimal(1000);

    //-------------------------------非实体字段-------------------------------
    // region 非实体字段

    /**
     * 用户id集合，用在批量删除用户的参数
     */
    @NotEmpty(message = "用户ID集合不能为空", groups = {batchDelete.class})
    private Set<Long> userIdList;

    // endregion

}
