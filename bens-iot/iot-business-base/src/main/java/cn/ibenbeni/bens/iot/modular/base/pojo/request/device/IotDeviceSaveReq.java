package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - IoT设备创建/更新入参")
public class IotDeviceSaveReq {

    @Schema(description = "设备ID", example = "10")
    private Long deviceId;

    @NotBlank(message = "设备名称不能为空")
    @Schema(description = "设备名称", example = "笨笨1号设备")
    private String deviceName;

    @Schema(description = "设备昵称", example = "笨笨1号设备昵称")
    private String deviceNickname;

    @NotBlank(message = "设备序列号不能为空")
    @Schema(description = "设备序列号", example = "A17600981283438UVI")
    private String deviceSn;

    @Schema(description = "设备图片", example = "https://www.baidu.com/link?url=r4BN")
    private String picUrl;

    @NotNull(message = "产品ID不能为空")
    @Schema(description = "产品ID", example = "101")
    private Long productId;

    @Schema(description = "设备位置的纬度", example = "116.36")
    private BigDecimal latitude;

    @Schema(description = "设备位置的经度", example = "39.91")
    private BigDecimal longitude;

    @Schema(description = "备注")
    private String remark;

}
