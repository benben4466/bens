package cn.ibenbeni.bens.rule.util;

import java.util.Arrays;

/**
 * Object 工具类
 */
public class ObjectUtils {

    public static <T> boolean equalsAny(T obj, T... objs) {
        return Arrays.asList(objs).contains(obj);
    }

}
