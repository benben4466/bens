package cn.ibenbeni.bens.iot.modular.base.entity.device;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * IOT产品分类实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_group", autoResultMap = true)
public class IotGroupDO extends BaseBusinessEntity {

    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    private Long groupId;

    /**
     * 设备分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 设备分组排序
     */
    @TableField("group_order")
    private BigDecimal groupOrder;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
