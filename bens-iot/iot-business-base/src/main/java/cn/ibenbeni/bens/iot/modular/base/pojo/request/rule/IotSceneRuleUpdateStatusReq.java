package cn.ibenbeni.bens.iot.modular.base.pojo.request.rule;

import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - IoT场景联动更新状入参")
public class IotSceneRuleUpdateStatusReq {

    @NotNull(message = "场景联动编号不能为空")
    @Schema(description = "场景联动编号", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ruleId;

    @NotNull(message = "状态不能为空")
    @InEnum(value = StatusEnum.class, message = "修改状态必须是 {value}")
    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer statusFlag;

}
