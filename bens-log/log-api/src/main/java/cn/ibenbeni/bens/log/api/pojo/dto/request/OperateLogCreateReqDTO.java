package cn.ibenbeni.bens.log.api.pojo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 操作日志创建请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLogCreateReqDTO {

    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 请求地址
     */
    @NotEmpty(message = "请求地址不能为空")
    private String requestUrl;

    /**
     * 请求方式
     */
    @NotEmpty(message = "请求方式不能为空")
    private String requestMethod;

    /**
     * 请求参数
     */
    @NotEmpty(message = "请求参数不能为空")
    private String requestParams;

    /**
     * 请求响应
     */
    @NotEmpty(message = "请求响应不能为空")
    private String requestResult;

    /**
     * 操作模块编号
     */
    @NotEmpty(message = "操作模块编号不能为空")
    private String moduleNo;

    /**
     * 操作子模块编号
     */
    @NotEmpty(message = "操作子模块编号不能为空")
    private String subModuleNo;

    /**
     * 操作模块业务ID
     */
    @NotNull(message = "操作模块业务ID不能为空")
    private Long bizId;

    /**
     * 操作内容
     */
    @NotEmpty(message = "操作内容不能为空")
    private String opAction;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 用户账号
     */
    @NotEmpty(message = "用户账号不能为空")
    private String userAccount;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户IP
     */
    @NotEmpty(message = "用户IP不能为空")
    private String userIp;

    /**
     * 浏览器UA
     */
    @NotEmpty(message = "浏览器UA不能为空")
    private String userAgent;

    /**
     * 服务器IP
     */
    @NotEmpty(message = "服务器IP不能为空")
    private String serverIp;

}
