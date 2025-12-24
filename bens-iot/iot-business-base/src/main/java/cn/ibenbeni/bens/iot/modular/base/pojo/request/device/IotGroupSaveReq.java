package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - IoT设备分组查询/更新入参")
public class IotGroupSaveReq {

    @Schema(description = "设备分组ID", example = "10")
    private Long groupId;

    @NotBlank(message = "设备分组名称不能为空")
    @Schema(description = "设备分组名称", example = "笨笨1号设备分组")
    private String groupName;

    @NotNull(message = "设备分组排序不能为空")
    @Schema(description = "设备分组排序", example = "0")
    private BigDecimal groupOrder;

    @Schema(description = "备注", example = "0")
    private String remark;

}
