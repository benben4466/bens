package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, Function.identity());
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, mergeFunc, HashMap::new);
    }

    /**
     * 将集合转换成Map
     *
     * @param from      待转换集合
     * @param keyFunc   获取Key的方法
     * @param valueFunc 获取Value的方法
     * @param mergeFunc key相同时, 合并Value的方法
     * @param supplier  创建Map的实现类
     * @param <T>       待转换元素类型
     * @param <K>       目标Key类型
     * @param <V>       目标Value类型
     * @return 目标Map
     */
    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunc, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }

        return from.stream()
                .collect(Collectors.toMap(keyFunc, valueFunc, mergeFunc, supplier));
    }

    /**
     * 将集合按照keyFunc进行分组
     *
     * @param from    待转换集合
     * @param keyFunc 获取key的方法
     * @param <T>     待转换元素类型
     * @param <R>     目标key类型
     * @return 目标Map
     */
    public static <T, R> Map<R, Long> convertMapGroup(Collection<T> from, Function<T, R> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.counting()));
    }

    public static <T, R> Set<R> convertSet(Collection<T> from, Function<T, R> func) {
        if (CollUtil.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream()
                .map(func)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <T> List<T> filterList(Collection<T> from, Predicate<T> predicate) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }

}
