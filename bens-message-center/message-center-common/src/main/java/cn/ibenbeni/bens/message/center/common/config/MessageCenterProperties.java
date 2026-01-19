package cn.ibenbeni.bens.message.center.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息中心统一配置
 *
 * @author bens
 */
@Data
@ConfigurationProperties(prefix = "bens.message-center")
public class MessageCenterProperties {

    /**
     * MQ 配置
     */
    private Queue queue = new Queue();

    /**
     * 渠道配置
     */
    private Channels channels = new Channels();

    /**
     * 敏感词配置
     */
    private Sensitive sensitive = new Sensitive();

    /**
     * 幂等性配置
     */
    private Idempotent idempotent = new Idempotent();

    /**
     * MQ 队列配置
     */
    @Data
    public static class Queue {
        private String topic = "MESSAGE_CENTER_SEND_TOPIC";
        private int consumerThreads = 4;
        private int maxRetry = 3;
        private int timeout = 30000;
    }

    /**
     * 渠道配置
     */
    @Data
    public static class Channels {
        private Email email = new Email();
        private Sms sms = new Sms();
        private Push push = new Push();
        private Letter letter = new Letter();
    }

    @Data
    public static class Email {
        private boolean enabled = false;
        private String supplier = "MAIL";
        private int timeout = 30000;
        private double qps = 100.0;
    }

    @Data
    public static class Sms {
        private boolean enabled = false;
        private String supplier = "aliyun";
        private int timeout = 10000;
        private double qps = 50.0;
    }

    @Data
    public static class Push {
        private boolean enabled = false;
        private String supplier = "jpush";
        private int timeout = 10000;
        private double qps = 1000.0;
    }

    @Data
    public static class Letter {
        private boolean enabled = true;
        private double qps = 500.0;
    }

    @Data
    public static class Sensitive {
        private boolean enabled = true;
        private String provider = "local";
        private int timeout = 5000;
    }

    @Data
    public static class Idempotent {
        private boolean enabled = true;
        private String keyPrefix = "msg:idempotent:";
        private long expireSeconds = 3600;
    }

}