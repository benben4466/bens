package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 角色分页请求")
public class RolePageReq extends PageParam {

    @Schema(description = "角色名称，模糊匹配", example = "笨笨角色")
    private String roleName;

    @Schema(description = "角色标识，模糊匹配", example = "admin")
    private String roleCode;

    @Schema(description = "角色状态", example = "1")
    private Integer statusFlag;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    private LocalDateTime[] createTime;

}
