package cn.ibenbeni.bens.message.center.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * 消息中心 ID 工具类
 */
public class MessageUniqueIdUtils {

    /**
     * 生成业务ID (BizId)
     * 使用 Hutool 雪花算法生成纯数字ID字符串
     *
     * @return BizId
     */
    public static String generateBizId() {
        return IdUtil.getSnowflakeNextIdStr();
    }

}
