package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.util.sort.GetSortKey;

import java.util.LinkedList;
import java.util.List;

/**
 * 排序工具类
 * <p>一般用来弥补数据库排序功能不足的情况</p>
 *
 * @author benben
 * @date 2025/5/3  下午7:16
 */
public class SortUtils {

    /**
     * 对list进行排序，以keys数组传的顺序为准
     *
     * @param originList 原始集合
     * @param keys       顺序集合
     * @param <T>        类型
     */
    public static <T extends GetSortKey> List<T> sortListByObjectKey(List<T> originList, List<?> keys) {
        if (ObjectUtil.isEmpty(originList) || ObjectUtil.isEmpty(keys)) {
            return originList;
        }

        List<T> newSortList = new LinkedList<>();
        for (Object key : keys) {
            for (T listItem : originList) {
                Object sortKey = listItem.getSortKey();
                if (ObjectUtil.equal(key, sortKey)) {
                    newSortList.add(listItem);
                }
            }
        }
        return newSortList;
    }

}

