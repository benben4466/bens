package cn.ibenbeni.bens.db.api.pojo.query;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

/**
 * 拓展 MyBatis-Plus QueryWrapper 类，主要增加如下功能：
 * <p>1.拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。</p>
 *
 * @param <T>
 */
public class LambdaUpdateWrapperX<T> extends LambdaUpdateWrapper<T> {
}
