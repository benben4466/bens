package cn.ibenbeni.bens.log.operatelog.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 操作日志响应")
@Data
@ExcelIgnoreUnannotated
public class OperateLogResp {

    @ExcelProperty("日志ID")
    @Schema(description = "日志ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long olgId;

    @Schema(description = "链路追踪编号", example = "123-abc-111-22", requiredMode = Schema.RequiredMode.REQUIRED)
    private String traceId;

    @Schema(description = "请求地址", example = "/api/xxx/yy", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestUrl;

    @Schema(description = "请求方式", example = "POST", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestMethod;

    @Schema(description = "请求参数", example = "{userId:10}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestParams;

    @Schema(description = "请求响应", example = "{userAccount:benben}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestResult;

    @ExcelProperty("操作模块编号")
    @Schema(description = "操作模块编号", example = "商品", requiredMode = Schema.RequiredMode.REQUIRED)
    private String moduleNo;

    @ExcelProperty("操作子模块编号")
    @Schema(description = "操作子模块编号", example = "查询商品信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subModuleNo;

    @ExcelProperty("操作模块业务ID")
    @Schema(description = "操作模块业务ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bizId;

    @Schema(description = "操作内容", example = "商品名称由笨笨食品修改为笨笨1号食品")
    private String opAction;

    @Schema(description = "用户ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @ExcelProperty("用户账号")
    @Schema(description = "用户账号", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAccount;

    @Schema(description = "用户IP", example = "127.0.0.1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userIp;

    @Schema(description = "用户IP", example = "Mozilla/5.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAgent;

    @ExcelProperty("创建时间")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
