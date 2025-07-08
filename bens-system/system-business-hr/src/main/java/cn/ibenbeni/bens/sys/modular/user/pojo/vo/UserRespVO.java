package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息响应
 *
 * @author: benben
 * @time: 2025/7/6 下午2:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRespVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户头像
     * <p>头像存储路径</p>
     */
    private String avatar;

    /**
     * 用户生日
     */
    private Date birthday;

    /**
     * 用户性别
     * <p>性别枚举：{@link cn.ibenbeni.bens.rule.enums.SexEnum}</p>
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户状态
     * <p>状态枚举：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    private Integer statusFlag;

    /**
     * 用户排序
     */
    private BigDecimal userSort;

    /**
     * 最后登陆IP
     */
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

}
