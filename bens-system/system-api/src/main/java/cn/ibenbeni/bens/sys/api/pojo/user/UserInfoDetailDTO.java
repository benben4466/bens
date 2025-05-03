package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 较为全面的用户基本信息
 *
 * @author benben
 * @date 2025/4/19  下午1:32
 */
@Data
@Builder
public class UserInfoDetailDTO {

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
     * 生日
     */
    private Date birthday;

    /**
     * 性别：M-男，F-女
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
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
    private BigDecimal userSort;

}
