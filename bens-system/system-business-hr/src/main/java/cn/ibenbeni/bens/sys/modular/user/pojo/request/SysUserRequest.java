package cn.ibenbeni.bens.sys.modular.user.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author benben
 * @date 2025/4/19  下午1:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRequest extends BaseRequest {

    /**
     * 主键
     */
    private Long userId;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码，加密方式为BCrypt
     */
    private String password;

    /**
     * 新密码
     */
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
     * 是否是超级管理员：Y-是，N-否
     */
    private String superAdminFlag;

    /**
     * 状态：1-正常，2-冻结，3-临时冻结
     */
    private Integer statusFlag;

    /**
     * 用户的排序
     */
    private BigDecimal userSort = new BigDecimal(1000);

    //-------------------------------非实体字段-------------------------------
    //-------------------------------非实体字段-------------------------------
    //-------------------------------非实体字段-------------------------------

    /**
     * 用户id集合，用在批量删除用户的参数
     */
    private Set<Long> userIdList;

}
