package cn.ibenbeni.bens.iot.modular.base.pojo.response.rule;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "管理后台 - IoT 场景联动响应")
public class IotSceneRuleResp {

    @Schema(description = "场景编号", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "场景名称", example = "笨笨产品1号场景规则测试", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "场景描述", example = "笨笨")
    private String description;

    @Schema(description = "场景状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "触发器数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<IotSceneRuleDO.Trigger> triggers;

    @Schema(description = "执行器数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<IotSceneRuleDO.Action> actions;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
