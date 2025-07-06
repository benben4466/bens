package cn.ibenbeni.bens.db.api.pojo.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.util.ArrayUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 拓展 MyBatis-Plus QueryWrapper 类，主要增加如下功能：
 * <p>1.拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。</p>
 *
 * @author: benben
 * @time: 2025/7/6 上午10:40
 */
public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {

    public LambdaQueryWrapperX<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (LambdaQueryWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (!ObjectUtils.isEmpty(val)) {
            return (LambdaQueryWrapperX<T>) super.eq(column, val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (ObjectUtil.isAllNotEmpty(values) && ArrayUtil.isNotEmpty(values)) {
            return (LambdaQueryWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = ArrayUtils.get(values, 0);
        Object val2 = ArrayUtils.get(values, 1);
        return this.betweenIfPresent(column, val1, val2);
    }

    public LambdaQueryWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        // val1 和 val2 都不为空时，拼接 between 条件
        if (val1 != null && val2 != null) {
            return (LambdaQueryWrapperX<T>) super.between(column, val1, val2);
        }

        // 仅 val1 不为空时，拼接大于等于条件
        if (val1 != null) {
            return (LambdaQueryWrapperX<T>) ge(column, val1);
        }
        // 仅 val2 不为空时，拼接小于等于条件
        if (val2 != null) {
            return (LambdaQueryWrapperX<T>) le(column, val2);
        }

        return this;
    }

    @Override
    public LambdaQueryWrapper<T> orderByDesc(SFunction<T, ?> column) {
        // condition: 是否使用该排序字段
        // column: 排序字段
        super.orderByDesc(true, column);
        return this;
    }

}
