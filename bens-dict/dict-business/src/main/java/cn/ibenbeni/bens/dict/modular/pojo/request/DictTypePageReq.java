package cn.ibenbeni.bens.dict.modular.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 字典类型分页列表入参")
public class DictTypePageReq extends PageParam {

    @NotBlank(message = "字典类型名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    @Schema(description = "字典类型名称", example = "笨笨字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeName;

    @NotBlank(message = "字典类型编码不能为空")
    @Size(max = 100, message = "字典类型编码长度不能超过100个字符")
    @Schema(description = "字典编码名称", example = "笨笨字典编码名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeCode;

    /**
     * <p>枚举: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @NotNull(message = "字典状态不能为空")
    @Schema(description = "字典状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
