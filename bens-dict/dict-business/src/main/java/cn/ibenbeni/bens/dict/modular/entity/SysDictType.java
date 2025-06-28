package cn.ibenbeni.bens.dict.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典类型表
 * <p>字典编码和字典名称唯一</p>
 *
 * @author: benben
 * @time: 2025/6/13 下午10:21
 */
@TableName(value = "sys_dict_type", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_ID)
    private Long dictTypeId;

    /**
     * 字典类型名称
     */
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 字典类型编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典类型编码
     * <p>1=业务类型；2=系统类型</p>
     */
    @TableField("dict_type_class")
    private Integer dictTypeClass;

    /**
     * 状态
     * <p>1=启用，2=禁用</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 字典类型排序
     */
    @TableField("dict_type_sort")
    private BigDecimal dictTypeSort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
