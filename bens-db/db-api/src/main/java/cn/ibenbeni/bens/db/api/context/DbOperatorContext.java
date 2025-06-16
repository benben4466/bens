package cn.ibenbeni.bens.db.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.DbOperatorApi;

/**
 * 获取SQL操作器
 *
 * @author: benben
 * @time: 2025/6/16 下午10:21
 */
public class DbOperatorContext {

    public static DbOperatorApi me() {
        return SpringUtil.getBean(DbOperatorApi.class);
    }

}
