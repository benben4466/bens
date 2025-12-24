package cn.ibenbeni.bens.iot.modular.base.pojo.request.rule;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "管理后台 - IoT 场景联动新增/修改入参")
public class IotSceneRuleSaveReq {

    @Schema(description = "场景编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "场景名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "笨笨1号测试场景1")
    @NotEmpty(message = "场景名称不能为空")
    private String name;

    @Schema(description = "场景描述", example = "你猜")
    private String description;

    @Schema(description = "场景状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "场景状态不能为空")
    @InEnum(StatusEnum.class)
    private Integer statusFlag;

    @Schema(description = "触发器数组", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "触发器数组不能为空")
    private List<IotSceneRuleDO.Trigger> triggers;

    @Schema(description = "执行器数组", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "执行器数组不能为空")
    private List<IotSceneRuleDO.Action> actions;

}
