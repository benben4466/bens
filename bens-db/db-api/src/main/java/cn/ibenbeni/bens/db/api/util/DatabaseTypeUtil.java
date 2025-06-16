package cn.ibenbeni.bens.db.api.util;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.enums.DbTypeEnum;

/**
 * 判断数据库类型的工具
 *
 * @author: benben
 * @time: 2025/6/16 下午10:45
 */
public class DatabaseTypeUtil {

    /**
     * 获取数据库类型
     * <p>默认MySQL数据库</p>
     *
     * @param jdbcUrl 数据库连接URL
     * @return 数据库类型枚举
     */
    public static DbTypeEnum getDbType(String jdbcUrl) {
        if (StrUtil.isBlank(jdbcUrl)) {
            return DbTypeEnum.MYSQL;
        }

        // URL字符串中包含了dbTypeEnum的name，则判定为该类型
        for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
            if (jdbcUrl.contains(dbTypeEnum.getUrlWords())) {
                return dbTypeEnum;
            }
        }

        return DbTypeEnum.MYSQL;
    }

}
