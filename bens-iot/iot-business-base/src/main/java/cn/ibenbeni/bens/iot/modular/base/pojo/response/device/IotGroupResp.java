package cn.ibenbeni.bens.iot.modular.base.pojo.response.device;

import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - IoT设备分组响应")
public class IotGroupResp {

    @Schema(description = "设备分组ID", example = "10")
    private Long groupId;

    @Schema(description = "设备分组名称", example = "笨笨1号设备分组")
    private String groupName;

    @Schema(description = "设备分组排序", example = "0")
    private BigDecimal groupOrder;

    @Schema(description = "备注", example = "0")
    private String remark;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
