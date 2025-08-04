package cn.ibenbeni.bens.log.loginlog.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 登录日志分页列表入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogPageReq extends PageParam {

    @Schema(description = "用户账号,模糊匹配", example = "benben")
    private String userAccount;

    @Schema(description = "登陆IP,模糊匹配", example = "127.0.0.1")
    private String loginIp;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "登录时间")
    private LocalDateTime[] createTime;

}
