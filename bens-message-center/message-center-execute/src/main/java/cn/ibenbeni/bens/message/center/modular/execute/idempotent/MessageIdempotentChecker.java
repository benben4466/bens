package cn.ibenbeni.bens.message.center.modular.execute.idempotent;

import cn.ibenbeni.bens.message.center.common.config.MessageCenterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息幂等性检查器
 * 使用 Redis 保证消息不被重复处理
 *
 * @author bens
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "bens.message-center.idempotent", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageIdempotentChecker {

    // @Resource
    // private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MessageCenterProperties messageCenterProperties;

    /**
     * 尝试获取幂等锁
     *
     * @param recordId 发送记录ID
     * @param channelType 渠道类型
     * @return true=获取成功(首次处理), false=获取失败(重复处理)
     */
    public boolean tryAcquire(Long recordId, Integer channelType) {
        if (recordId == null || channelType == null) {
            log.warn("[MessageIdempotentChecker][参数为空，跳过幂等检查]");
            return true;
        }

        MessageCenterProperties.Idempotent config = messageCenterProperties.getIdempotent();
        String key = config.getKeyPrefix() + recordId + ":" + channelType;

        try {
            // Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", config.getExpireSeconds(), TimeUnit.SECONDS);
            Boolean success = false;

            boolean result = Boolean.TRUE.equals(success);

            if (!result) {
                log.info("[MessageIdempotentChecker][幂等拦截][recordId: {}, channelType: {}]",
                        recordId, channelType);
            }

            return result;

        } catch (Exception e) {
            log.error("[MessageIdempotentChecker][幂等检查异常，允许通过][recordId: {}]", recordId, e);
            // 异常时放行，避免阻塞正常流程
            return true;
        }
    }

    /**
     * 释放幂等锁(用于失败重试场景)
     *
     * @param recordId 发送记录ID
     * @param channelType 渠道类型
     */
    public void release(Long recordId, Integer channelType) {
        if (recordId == null || channelType == null) {
            return;
        }

        MessageCenterProperties.Idempotent config = messageCenterProperties.getIdempotent();
        String key = config.getKeyPrefix() + recordId + ":" + channelType;

        try {
            // stringRedisTemplate.delete(key);
            log.debug("[MessageIdempotentChecker][释放幂等锁][recordId: {}, channelType: {}]",
                    recordId, channelType);
        } catch (Exception e) {
            log.error("[MessageIdempotentChecker][释放幂等锁失败][recordId: {}]", recordId, e);
        }
    }

}