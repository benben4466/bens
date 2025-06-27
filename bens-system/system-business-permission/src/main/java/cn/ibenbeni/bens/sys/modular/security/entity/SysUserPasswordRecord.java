package cn.ibenbeni.bens.sys.modular.security.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户历史密码记录实例类
 *
 * @author benben
 * @date 2025/5/3  下午4:21
 */
@TableName(value = "sys_user_password_record", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPasswordRecord extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 历史密码
     */
    @TableField("history_password")
    private String historyPassword;

    /**
     * 历史密码盐
     */
    @TableField("history_password_salt")
    private String historyPasswordSalt;

    /**
     * 更新密码时间
     */
    @TableField("update_password_time")
    private Date updatePasswordTime;

}
