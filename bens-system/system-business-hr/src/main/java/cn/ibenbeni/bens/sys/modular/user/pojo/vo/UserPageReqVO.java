package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
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
     * <p>模糊匹配</p>
     */
    private String account;

    /**
     * 手机号码
     * <p>模糊匹配</p>
     */
    private String phone;

    /**
     * 用户状态
     * <p>状态枚举：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    private Integer statusFlag;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
