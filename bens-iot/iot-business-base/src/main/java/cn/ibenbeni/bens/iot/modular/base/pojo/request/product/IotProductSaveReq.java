package cn.ibenbeni.bens.iot.modular.base.pojo.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - IoT产品新增/修改入参")
public class IotProductSaveReq {

    @Schema(description = "产品ID", example = "10")
    private Long productId;

    @NotBlank(message = "产品名称不能为空")
    @Schema(description = "产品名称", example = "笨笨1号产品")
    private String productName;

    @NotBlank(message = "产品Key不能为空")
    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "产品图标", example = "https://www.baidu.com/link?url=r4BN")
    private String productIcon;

    @NotNull(message = "产品分类ID不能为空")
    @Schema(description = "产品分类ID", example = "100")
    private Long categoryId;

    @NotNull(message = "产品分类名称不能为空")
    @Schema(description = "产品分类名称", example = "笨笨产品")
    private String categoryName;

    @NotNull(message = "产品状态不能为空")
    @Schema(description = "产品状态", example = "0")
    private Integer statusFlag;

    @NotNull(message = "是否系统内置不能为空")
    @Schema(description = "是否系统内置", example = "0")
    private Integer isSys;

    @NotNull(message = "设备类型不能为空")
    @Schema(description = "设备类型", example = "0")
    private Integer deviceType;

    @NotNull(message = "联网方式不能为空")
    @Schema(description = "联网方式", example = "0")
    private Integer networkMethod;

    @NotNull(message = "认证方式不能为空")
    @Schema(description = "认证方式", example = "0")
    private Integer authMethod;

    @NotBlank(message = "通信协议编码不能为空")
    @Schema(description = "通信协议编码", example = "MQTT")
    private String protocolCode;

    @Schema(description = "备注")
    private String remark;

}
