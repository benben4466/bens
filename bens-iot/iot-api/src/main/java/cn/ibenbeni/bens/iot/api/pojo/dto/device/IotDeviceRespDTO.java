package cn.ibenbeni.bens.iot.api.pojo.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IOT-设备信息-响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotDeviceRespDTO {

    // region 设备相关字段

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 产品Key
     */
    private String productKey;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备SN
     */
    private String deviceSn;

    /**
     * 租户ID
     */
    private Long tenantId;

    // endregion

    // region 产品相关字段

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 数据格式，即编解码类型
     */
    private String dataFormat;

    // endregion

}
