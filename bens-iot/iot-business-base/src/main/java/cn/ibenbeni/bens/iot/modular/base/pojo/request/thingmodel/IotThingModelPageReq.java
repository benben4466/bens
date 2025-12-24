package cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT物模型分页入参")
public class IotThingModelPageReq extends PageParam {

    @Schema(description = "产品ID", example = "18")
    private Long productId;

    @Schema(description = "模型名称", example = "temperature")
    private String name;

    @Schema(description = "模型标识(同产品下唯一)", example = "temperature")
    private String identifier;

    @Schema(description = "模型类型", example = "1")
    private Integer type;

    @Schema(description = "是否历史存储", example = "1")
    private Integer isHistory;

}
