package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.date.DateUtil;

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

}
