package cn.ibenbeni.bens.db.api;

import cn.ibenbeni.bens.rule.enums.DbTypeEnum;

/**
 * 数据库操作的API
 * <p>用于快速进行SQL操作并获取结果</p>
 *
 * @author: benben
 * @time: 2025/6/16 下午10:22
 */
public interface DbOperatorApi {

    /**
     * 获取当前数据库连接的数据库类型
     */
    DbTypeEnum getCurrentDbType();

    /**
     * 返回SelectCount SQL执行的结果
     *
     * @param sql  带有select count()相关语句的SQL
     * @param args SQL中的参数
     * @return SQL执行的结果，取第一行第一个数字
     */
    int selectCount(String sql, Object... args);

}
