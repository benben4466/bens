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
public class SysDictDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典类型编码
     * <p>冗余字段</p>
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典值
     */
    @TableField("dict_value")
    private String dictValue;

    /**
     * 颜色类型
     * <p>给前端使用，显示状态框颜色</p>
     */
    @TableField("dict_color_type")
    private String dictColorType;

    /**
     * 显示排序
     */
    @TableField("dict_sort")
    private BigDecimal dictSort;

    /**
     * 状态
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
