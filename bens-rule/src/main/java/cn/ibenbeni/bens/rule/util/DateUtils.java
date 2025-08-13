package cn.ibenbeni.bens.rule.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author: benben
 * @time: 2025/7/3 下午11:02
 */
public class DateUtils {

    /**
     * 默认时区
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转换成LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime convertLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    /**
     * 判断时间是否过期
     *
     * @param date Date对象
     * @return true=过期；false=未过期
     */
    public static boolean isExpired(Date date) {
        return new Date().after(date);
    }

}
