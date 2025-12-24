package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT设备分组分页入参")
public class IotGroupPageReq extends PageParam {

    @Schema(description = "设备分组名称", example = "笨笨1号设备分组")
    private String groupName;

}
