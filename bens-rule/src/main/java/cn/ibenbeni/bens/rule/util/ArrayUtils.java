package cn.ibenbeni.bens.rule.util;

/**
 * Array 工具类
 *
 * @author: benben
 * @time: 2025/7/6 上午10:52
 */
public class ArrayUtils {

    public static <T> T get(T[] array, int index) {
        if (array == null || index >= array.length) {
            return null;
        }
        return array[index];
    }

}
