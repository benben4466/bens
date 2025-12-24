package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * 时间戳工具类
 */
public class TimestampUtils {

    /**
     * @return UTC时间戳
     */
    public static long curUtcMillis() {
        return DateUtil.current();
    }

    /**
     * LocalDateTime 转为 UTC 时间戳
     *
     * @param localDateTime 时间
     * @return UTC时间戳
     */
    public static Long toUtcMillis(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0L;
        }

        // 1. 转为 Instant（UTC 时间）
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        // 2. 转为毫秒级时间戳
        long timestamp = instant.toEpochMilli();

        // 3. 如果只有 10 位（秒级），自动转毫秒
        if (String.valueOf(timestamp).length() == 10) {
            timestamp = timestamp * 1000;
        }
        return timestamp;
    }

    /**
     * LocalDateTime 转为毫秒级时间戳（时区：系统默认）
     *
     * @param localDateTime 时间
     * @return 毫秒级时间戳
     */
    public static Long toMillis(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0L;
        }

        // LocalDateTime → Instant（带系统时区）
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long timestamp = instant.toEpochMilli();

        // 若为10位（秒级），则转成毫秒级
        if (String.valueOf(timestamp).length() == 10) {
            timestamp *= 1000;
        }

        return timestamp;
    }

}
