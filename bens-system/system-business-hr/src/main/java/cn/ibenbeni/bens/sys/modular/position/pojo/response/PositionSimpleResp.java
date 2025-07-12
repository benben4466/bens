package cn.ibenbeni.bens.sys.modular.position.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 职位信息的精简响应
 *
 * @author: benben
 * @time: 2025/7/12 下午3:22
 */
@Data
@Schema(description = "管理后台 - 职位信息的精简响应")
public class PositionSimpleResp {

    @Schema(description = "职位序号", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long positionId;

    @Schema(description = "职位名称", example = "员工", requiredMode = Schema.RequiredMode.REQUIRED)
    private String positionName;

}
