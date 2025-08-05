package cn.ibenbeni.bens.log.api.pojo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志响应-DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLogRespDTO {

    /**
     * 主键
     */
    private Long olgId;

    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求响应
     */
    private String requestResult;

    /**
     * 操作模块编号
     */
    private String moduleNo;

    /**
     * 操作子模块编号
     */
    private String subModuleNo;

    /**
     * 操作模块业务ID
     */
    private Long bizId;

    /**
     * 操作内容
     */
    private String opAction;

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
    private Integer userType;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 浏览器UA
     */
    private String userAgent;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 扩展字段
     */
    private String expandField;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
