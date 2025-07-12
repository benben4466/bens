package cn.ibenbeni.bens.sys.modular.position.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 职位创建/修改请求参数
 *
 * @author: benben
 * @time: 2025/7/12 下午1:41
 */
@Data
@Schema(description = "管理后台 - 职位创建/修改请求参数")
public class PositionSaveReq extends BaseRequest {

    /**
     * 职位ID
     */
    @Schema(description = "职位ID", example = "10")
    @NotNull(message = "职位ID不能为空", groups = {update.class})
    private Long positionId;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称", example = "员工")
    @NotBlank(message = "职位名称不能为空", groups = {create.class, update.class})
    private String positionName;

    /**
     * 职位编码
     */
    @Schema(description = "职位编码", example = "staff")
    @NotBlank(message = "职位编码不能为空", groups = {create.class, update.class})
    private String positionCode;

    /**
     * 职位排序
     */
    @Schema(description = "职位排序", example = "1.0")
    @NotNull(message = "职位排序不能为空", groups = {create.class, update.class})
    private BigDecimal positionSort;

    /**
     * 职位状态
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "职位状态", example = "1")
    @NotNull(message = "职位状态不能为空", groups = {create.class, update.class})
    private Integer statusFlag;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
