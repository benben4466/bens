package cn.ibenbeni.bens.log.operatelog.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志实体
 */
@TableName(value = "sys_operate_log", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OperateLogDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "olg_id", type = IdType.ASSIGN_ID)
    private Long olgId;

    /**
     * 链路追踪编号
     */
    @TableField(value = "trace_id")
    private String traceId;

    /**
     * 请求地址
     */
    @TableField(value = "request_url")
    private String requestUrl;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 请求响应
     */
    @TableField(value = "request_result")
    private String requestResult;

    /**
     * 操作模块编号
     */
    @TableField(value = "module_no")
    private String moduleNo;

    /**
     * 操作子模块编号
     */
    @TableField(value = "sub_module_no")
    private String subModuleNo;

    /**
     * 操作模块业务ID
     */
    @TableField(value = "biz_id")
    private Long bizId;

    /**
     * 操作内容
     */
    @TableField(value = "op_action")
    private String opAction;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 用户IP
     */
    @TableField(value = "user_ip")
    private String userIp;

    /**
     * 浏览器UA
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 服务器IP
     */
    @TableField(value = "server_ip")
    private String serverIp;

}
