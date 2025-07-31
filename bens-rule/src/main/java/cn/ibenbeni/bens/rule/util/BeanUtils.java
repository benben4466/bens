package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Bean工具类
 *
 * <p>基于 {@link BeanUtil} 实现</p>
 *
 * @author: benben
 * @time: 2025/7/8 上午10:18
 */
public class BeanUtils {

    /**
     * 将 source 转为 目标类型
     *
     * @param source     待转换对象
     * @param targetType 目标类型
     * @param <T>        目标对象类型
     * @return 目标对象
     */
    public static <T> T toBean(Object source, Class<T> targetType) {
        return BeanUtil.toBean(source, targetType);
    }

    public static <T, R> List<R> toBean(List<T> list, Class<R> targetType) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return CollectionUtils.convertList(list, t -> toBean(t, targetType));
    }

    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        if (target != null) {
            peek.accept(target);
        }
        return target;
    }

}
