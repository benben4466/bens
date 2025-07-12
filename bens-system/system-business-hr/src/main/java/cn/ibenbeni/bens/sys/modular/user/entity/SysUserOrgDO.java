package cn.ibenbeni.bens.sys.modular.user.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户组织机构关联实体
 *
 * @author: benben
 * @time: 2025/7/8 下午9:52
 */
@TableName(value = "sys_user_org", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserOrgDO extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "user_org_id", type = IdType.ASSIGN_ID)
    private Long userOrgId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 组织ID
     */
    @TableField(value = "org_id")
    private Long orgId;

    /**
     * 职位ID
     */
    @TableField(value = "position_id")
    private Long positionId;

    /**
     * 是否是主部门
     * <p>枚举类型：{@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @TableField("main_flag")
    private String mainFlag;

    /**
     * 是否启用
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

}
