package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author: benben
 * @time: 2025/7/6 下午3:03
 */
public class CollectionUtils {

    /**
     * 集合转换
     * <p>将 T类型转换为 R类型</p>
     *
     * @param from 待转换集合
     * @param func 转换方法
     * @param <T>  待转换元素类型
     * @param <R>  目标元素类型
     * @return 目标元素类型集合
     */
    public static <T, R> List<R> convertList(Collection<T> from, Function<T, R> func) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream()
                .map(func)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
