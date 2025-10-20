package cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel;

import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelEvent;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelProperty;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - IoT物模型新增/修改入参")
public class IotThingModelSaveReq {

    @Schema(description = "模型ID", example = "10")
    private Long modelId;

    @NotBlank(message = "模型名称不能为空")
    @Schema(description = "模型名称", example = "temperature")
    private String name;

    @NotBlank(message = "模型标识不能为空")
    @Schema(description = "模型标识(同产品下唯一)", example = "temperature")
    private String identifier;

    @NotNull(message = "模型类型不能为空")
    @Schema(description = "模型类型", example = "1")
    private Integer type;

    @NotNull(message = "产品ID不能为空")
    @Schema(description = "产品ID", example = "18")
    private Long productId;

    @NotNull(message = "产品Key不能为空")
    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "是否实时监测", example = "1")
    private Integer isMonitor;

    @Schema(description = "是否历史存储", example = "1")
    private Integer isHistory;

    @NotNull(message = "物模型排序不能为空")
    @Schema(description = "物模型排序")
    private BigDecimal modelSort;

    @Valid
    @Schema(description = "属性", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelProperty property;

    @Valid
    @Schema(description = "服务", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelService service;

    @Valid
    @Schema(description = "事件", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelEvent event;

    @Schema(description = "备注")
    private String remark;

}
