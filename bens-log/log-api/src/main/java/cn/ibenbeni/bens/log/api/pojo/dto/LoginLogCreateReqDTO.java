package cn.ibenbeni.bens.log.api.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 创建登陆日志入参DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogCreateReqDTO {

    /**
     * 日志类型
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginLogTypeEnum}</p>
     */
    @NotNull(message = "日志类型不能为空")
    private Integer llgType;

    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 登陆结果
     * <p>枚举: {@link cn.ibenbeni.bens.log.api.enums.LoginResultEnum}</p>
     */
    @NotNull(message = "登陆结果不能为空")
    private Integer loginResult;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户登陆IP
     */
    @NotNull(message = "用户登陆IP不能为空")
    private String loginIp;

    /**
     * 浏览器UA
     */
    private String userAgent;

}
