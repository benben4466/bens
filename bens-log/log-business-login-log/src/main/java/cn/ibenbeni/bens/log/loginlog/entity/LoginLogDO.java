package cn.ibenbeni.bens.log.loginlog.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志实体
 */
@TableName(value = "sys_login_log", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "llg_id", type = IdType.ASSIGN_ID)
    private Long llgId;

    /**
     * 日志类型
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginLogTypeEnum}</p>
     */
    @TableField(value = "llg_type")
    private Integer llgType;

    /**
     * 链路追踪编号
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 登陆结果
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginResultEnum}</p>
     */
    @TableField(value = "login_result")
    private Integer loginResult;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 用户登陆IP
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 浏览器UA
     */
    @TableField(value = "user_agent")
    private String userAgent;

}
