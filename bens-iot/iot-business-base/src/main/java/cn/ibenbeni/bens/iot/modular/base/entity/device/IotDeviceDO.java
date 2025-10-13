package cn.ibenbeni.bens.iot.modular.base.entity.device;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "iot_device", autoResultMap = true)
public class IotDeviceDO extends BaseBusinessEntity {

    /**
     * 设备ID
     */
    @TableId(value = "device_id", type = IdType.ASSIGN_ID)
    private Long deviceId;

    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 设备昵称
     */
    @TableField("device_nickname")
    private String deviceNickname;

    /**
     * 设备序列号
     */
    @TableField("device_sn")
    private String deviceSn;

    /**
     * 设备图片
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 产品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 产品Key
     */
    @TableField("product_key")
    private String productKey;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private Integer deviceType;

    /**
     * 设备状态
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 最后上线时间
     */
    @TableField("online_time")
    private Long onlineTime;

    /**
     * 最后离线时间
     */
    @TableField("offline_time")
    private Long offlineTime;

    /**
     * 设备激活时间
     */
    @TableField("active_time")
    private Long activeTime;

    /**
     * 设备IP
     */
    @TableField("network_ip")
    private String networkIp;

    /**
     * 设备密钥
     */
    @TableField("device_secret")
    private String deviceSecret;

    /**
     * 设备位置的纬度
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 设备位置的经度
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 设备所在地址
     */
    @TableField("device_address")
    private String deviceAddress;

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
