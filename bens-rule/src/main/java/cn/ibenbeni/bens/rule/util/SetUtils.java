package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * Set工具类
 */
public class SetUtils {

    public static <T> Set<T> asSet(T... objs) {
        return CollUtil.newHashSet(objs);
    }

}
