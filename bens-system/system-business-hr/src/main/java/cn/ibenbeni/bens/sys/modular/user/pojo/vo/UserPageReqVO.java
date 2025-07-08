package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.ibenbeni.bens.rule.util.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 用户分页查询请求参数
 *
 * @author: benben
 * @time: 2025/7/6 下午2:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageReqVO extends PageParam {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号, 模糊匹配", example = "benben")
    private String account;

    /**
     * 手机号码
     */
    @Schema(description = "用户号码, 模糊匹配", example = "12345678976")
    private String phone;

    /**
     * 用户状态
     * <p>状态枚举：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "用户状态", example = "1")
    private Integer statusFlag;

    /**
     * 创建时间
     */
    @Schema(description = "用户创建时间", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
