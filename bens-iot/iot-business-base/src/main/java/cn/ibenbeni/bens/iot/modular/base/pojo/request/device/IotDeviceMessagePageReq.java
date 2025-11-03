package cn.ibenbeni.bens.iot.modular.base.pojo.request.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.rule.util.DateUtils;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT设备消息分页查询入参")
public class IotDeviceMessagePageReq extends PageParam {

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "设备编号不能为空")
    private Long deviceId;

    @Schema(description = "消息类型", example = "property")
    @InEnum(IotDeviceMessageMethodEnum.class)
    private String method;

    @Schema(description = "是否上行", example = "true")
    private Boolean upstream;

    @Schema(description = "是否回复", example = "true")
    private Boolean reply;

    @Schema(description = "标识符", example = "temperature")
    private String identifier;

    @Schema(description = "时间范围", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Size(min = 2, max = 2, message = "请选择时间范围")
    private LocalDateTime[] times;

}
