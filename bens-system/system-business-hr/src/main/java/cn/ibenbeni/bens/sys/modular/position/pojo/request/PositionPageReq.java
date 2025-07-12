package cn.ibenbeni.bens.sys.modular.position.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职位分页请求参数
 *
 * @author: benben
 * @time: 2025/7/12 下午1:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 职位分页请求参数")
public class PositionPageReq extends PageParam {

    /**
     * 职位名称
     */
    @Schema(description = "职位名称", example = "员工")
    private String positionName;

    /**
     * 职位编码
     */
    @Schema(description = "职位编码", example = "staff")
    private String positionCode;

    /**
     * 职位状态
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "职位状态", example = "1")
    private Integer statusFlag;

}
