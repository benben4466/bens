package cn.ibenbeni.bens.sys.modular.position.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 职位信息响应
 *
 * @author: benben
 * @time: 2025/7/12 下午3:05
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "管理后台 - 职位信息响应")
public class PositionResp {

    /**
     * 职位ID
     */
    @ExcelProperty("岗位序号")
    @Schema(description = "职位ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long positionId;

    /**
     * 职位名称
     */
    @ExcelProperty("职位名称")
    @Schema(description = "职位名称", example = "员工", requiredMode = Schema.RequiredMode.REQUIRED)
    private String positionName;

    /**
     * 职位编码
     */
    @ExcelProperty("职位编码")
    @Schema(description = "职位编码", example = "staff", requiredMode = Schema.RequiredMode.REQUIRED)
    private String positionCode;

    /**
     * 职位排序
     */
    @ExcelProperty("职位排序")
    @Schema(description = "职位排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal positionSort;

    /**
     * 职位状态
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @ExcelProperty("职位状态")
    @Schema(description = "职位状态", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @Schema(description = "职位ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date createTime;

}
