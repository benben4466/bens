package cn.ibenbeni.bens.log.loginlog.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 登录日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LoginLogResp {

    @ExcelProperty("日志ID")
    @Schema(description = "日志ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long llgId;

    /**
     * 日志类型
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginLogTypeEnum}</p>
     */
    @ExcelProperty("日志类型")
    @Schema(description = "日志类型", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer llgType;

    @Schema(description = "链路追踪编号", example = "asdasd9-adasdasd-12888")
    private String traceId;

    /**
     * 登陆结果
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginResultEnum}</p>
     */
    @ExcelProperty("登陆结果")
    @Schema(description = "登陆结果", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer loginResult;

    @Schema(description = "用户ID", example = "10")
    private Long userId;

    @ExcelProperty("用户账号")
    @Schema(description = "用户账号", example = "benben", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAccount;

    @Schema(description = "用户类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userType;

    @ExcelProperty("用户登陆IP")
    @Schema(description = "用户登陆IP", example = "127.0.0.1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginIp;

    @ExcelProperty("浏览器UA")
    @Schema(description = "浏览器UA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAgent;

    @ExcelProperty("登录时间")
    @Schema(description = "登录时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
