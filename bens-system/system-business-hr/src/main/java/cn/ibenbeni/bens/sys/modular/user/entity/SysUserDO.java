package cn.ibenbeni.bens.sys.modular.user.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.rule.util.sort.GetSortKey;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 系统用户实例类
 *
 * @author benben
 * @date 2025/4/19  下午1:21
 */
@TableName(value = "sys_user", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserDO extends BaseBusinessEntity implements GetSortKey {

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     * <p>加密方式：md5+盐</p>
     */
    @TableField("password")
    private String password;

    /**
     * 密码盐
     * <p>加密方式：md5+盐</p>
     */
    @TableField("password_salt")
    private String passwordSalt;

    /**
     * 头像
     * <p>头像存储路径</p>
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 性别
     * <p>性别：M-男，F-女</p>
     */
    @TableField("sex")
    private String sex;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 是否是超级管理员
     * <p>Y-是，N-否</p>
     */
    @TableField("super_admin_flag")
    private String superAdminFlag;

    /**
     * 状态
     * <p>1-正常，2-冻结，3-临时冻结</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 冻结截止时间
     */
    @TableField(value = "freeze_deadline_time", updateStrategy = FieldStrategy.ALWAYS, insertStrategy = FieldStrategy.ALWAYS)
    private Date freezeDeadlineTime;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Long loginCount;

    /**
     * 最后登陆IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 用户的排序
     */
    @TableField("user_sort")
    private BigDecimal userSort;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 获取用户角色ID列表
     * <p>用在获取用户详情信息的响应</p>
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

    @Override
    public Object getSortKey() {
        return userId;
    }

}
