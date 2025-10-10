package cn.ibenbeni.bens.iot.modular.base.entity.product;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * IOT产品分类实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_product", autoResultMap = true)
public class IotProductDO extends BaseBusinessEntity {

    /**
     * 产品ID
     */
    @TableId(value = "product_id", type = IdType.ASSIGN_ID)
    private Long productId;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 产品Key
     */
    @TableField("product_key")
    private String productKey;

    /**
     * 产品图标
     */
    @TableField("product_icon")
    private String productIcon;

    /**
     * 产品分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 产品分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 产品状态
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 是否系统内置
     */
    @TableField("is_sys")
    private Integer isSys;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private Integer deviceType;

    /**
     * 联网方式
     */
    @TableField("network_method")
    private Integer networkMethod;

    /**
     * 认证方式
     */
    @TableField("auth_method")
    private Integer authMethod;

    /**
     * 通信协议编码
     */
    @TableField("protocol_code")
    private String protocolCode;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
