package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - IoT设备分组创建/更新入参")
public class IotDeviceGroupSaveReq {

    @NotNull
    @Schema(description = "设备ID")
    private Long deviceId;

    @NotNull
    @Schema(description = "设备分组ID")
    private Long deviceGroupId;

}
