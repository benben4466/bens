package cn.ibenbeni.bens.iot.modular.base.pojo.response.thingmodel;

import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelEvent;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelProperty;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - IoT物模型响应")
public class IotThingModelResp {

    @Schema(description = "模型ID", example = "10")
    private Long modelId;

    @Schema(description = "模型名称", example = "temperature")
    private String name;

    @Schema(description = "模型标识(同产品下唯一)", example = "temperature")
    private String identifier;

    @Schema(description = "模型类型", example = "1")
    private Integer type;

    @Schema(description = "产品ID", example = "18")
    private Long productId;

    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "是否实时监测", example = "1")
    private Integer isMonitor;

    @Schema(description = "是否历史存储", example = "1")
    private Integer isHistory;

    @Schema(description = "物模型排序")
    private BigDecimal modelSort;

    @Schema(description = "属性", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelProperty property;

    @Schema(description = "服务", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelService service;

    @Schema(description = "事件", requiredMode = Schema.RequiredMode.REQUIRED)
    private ThingModelEvent event;

    @Schema(description = "备注")
    private String remark;

}
