package cn.ibenbeni.bens.iot.modular.base.entity.device;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TenantIgnore
@TableName(value = "iot_device_group", autoResultMap = true)
public class IotDeviceGroupDO extends BaseEntity {

    /**
     * 设备ID
     */
    @TableField("device_id")
    private Long deviceId;

    /**
     * 设备分组ID
     */
    @TableField("device_group_id")
    private Long deviceGroupId;

}
