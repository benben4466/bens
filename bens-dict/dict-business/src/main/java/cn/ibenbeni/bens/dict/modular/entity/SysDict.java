package cn.ibenbeni.bens.dict.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 字典实体
 * <p>字典名称在同一字典类型下唯一</p>
 *
 * @author: benben
 * @time: 2025/6/13 下午11:43
 */
@TableName(value = "sys_dict", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseBusinessEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典类型ID
     */
    @TableField("dict_type_id")
    private Long dictTypeId;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典值
     */
    @TableField("dict_value")
    private String dictValue;

    /**
     * 字典简称名称
     */
    @TableField("dict_short_name")
    private String dictShortName;

    /**
     * 排序
     */
    @TableField("dict_sort")
    private BigDecimal dictSort;

    /**
     * 状态：0-禁用，1-正常
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    // -----------------------------------------------------非实体字段-------------------------------------------------
    // region 非实体字段

    /**
     * 字典类型的名称
     */
    @TableField(exist = false)
    private String dictTypeName;

    // endregion

}
