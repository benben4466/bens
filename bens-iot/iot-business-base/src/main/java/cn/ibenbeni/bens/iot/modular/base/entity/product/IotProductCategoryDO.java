package cn.ibenbeni.bens.iot.modular.base.entity.product;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.rule.enums.IsSysEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * IOT产品分类实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_product_category", autoResultMap = true)
public class IotProductCategoryDO extends BaseBusinessEntity {

    /**
     * 产品分类ID
     */
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private Long categoryId;

    /**
     * 产品分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 产品分类编码
     */
    @TableField("category_code")
    private String categoryCode;

    /**
     * 产品分类排序
     */
    @TableField("category_sort")
    private Double categorySort;


    /**
     * 产品分类状态
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 是否系统内置
     * <p>枚举: {@link IsSysEnum}</p>
     */
    @TableField("is_sys")
    private Integer isSys;

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
