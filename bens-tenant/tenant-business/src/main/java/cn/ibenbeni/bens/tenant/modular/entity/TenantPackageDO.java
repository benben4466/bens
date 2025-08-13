package cn.ibenbeni.bens.tenant.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.Set;

/**
 * 租户套餐实体
 * <p>该表要忽略租户</p>
 *
 * @author: benben
 * @time: 2025/6/30 下午5:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant_package", autoResultMap = true)
public class TenantPackageDO extends BaseBusinessEntity {

    /**
     * 租户套餐编号
     */
    @TableId(value = "package_id", type = IdType.ASSIGN_ID)
    private Long packageId;

    /**
     * 租户套餐名称
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 租户套餐关联的菜单编号
     */
    @TableField(value = "package_menu_ids", typeHandler = JacksonTypeHandler.class)
    private Set<Long> packageMenuIds;

    /**
     * 状态
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
