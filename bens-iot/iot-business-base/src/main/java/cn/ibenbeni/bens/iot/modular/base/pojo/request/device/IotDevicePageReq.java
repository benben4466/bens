package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT设备分页入参")
public class IotDevicePageReq extends PageParam {

    @Schema(description = "设备名称", example = "笨笨1号设备")
    private String deviceName;

    @Schema(description = "设备序列号", example = "A17600981283438UVI")
    private String deviceSn;

    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "设备类型", example = "0")
    private Integer deviceType;

    @Schema(description = "设备状态", example = "0")
    private Integer statusFlag;

}
