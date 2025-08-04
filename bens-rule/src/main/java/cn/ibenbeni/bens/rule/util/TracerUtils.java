package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.lang.UUID;

/**
 * 链路追踪工具类
 */
public class TracerUtils {

    private TracerUtils() {
    }

    /**
     * 获取链路追踪编号
     *
     * @return 链路追踪编号
     */
    public static String getTraceId() {
        // TODO 后期可采用SkyWalking
        return UUID.fastUUID().toString();
    }

}
