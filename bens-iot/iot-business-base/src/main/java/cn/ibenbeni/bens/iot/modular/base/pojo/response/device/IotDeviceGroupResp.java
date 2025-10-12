package cn.ibenbeni.bens.iot.modular.base.pojo.response.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - IoT设备分组关系响应")
public class IotDeviceGroupResp {

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "设备分组ID")
    private Long deviceGroupId;

}
