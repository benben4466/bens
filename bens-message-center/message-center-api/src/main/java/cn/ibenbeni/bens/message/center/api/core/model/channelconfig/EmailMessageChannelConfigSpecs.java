package cn.ibenbeni.bens.message.center.api.core.model.channelconfig;

import lombok.*;

/**
 * 邮件渠道配置参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailMessageChannelConfigSpecs extends AbstractMessageChannelConfigSpecs {

    /**
     * SMTP服务器地址
     */
    private String smtpServerAddress;

    /**
     * 发送端口
     */
    private Integer sendPort;

    /**
     * 发送人用户名
     */
    private String senderUsername;

    /**
     * 发送人用户名
     */
    private String senderPassword;

    /**
     * 发送人邮箱
     */
    private String senderEmail;

    /**
     * 是否开启认证
     */
    private Boolean isAuth;

    /**
     * 是否开启SSL
     */
    private Boolean isSSL;

    /**
     * 重试间隔(秒)
     */
    private Integer retryInterval;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

}
