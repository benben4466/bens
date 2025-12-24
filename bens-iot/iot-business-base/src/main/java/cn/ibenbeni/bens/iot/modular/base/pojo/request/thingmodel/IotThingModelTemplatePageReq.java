package cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT物模型模板分页入参")
public class IotThingModelTemplatePageReq extends PageParam {

    @Schema(description = "模型名称", example = "temperature")
    private String name;

    @Schema(description = "模型类型", example = "1")
    private Integer type;

}
