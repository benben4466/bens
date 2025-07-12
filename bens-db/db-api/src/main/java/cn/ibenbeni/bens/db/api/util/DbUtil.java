package cn.ibenbeni.bens.db.api.util;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.util.BeanUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * DB工具
 *
 * @author: benben
 * @time: 2025/7/12 下午3:29
 */
public class DbUtil {

    public static <T, R> PageResult<R> toBean(PageResult<T> source, Class<R> targetType) {
        return toBean(source, targetType, null);
    }

    /**
     * 转换PageResult内对象为指定类实例的PageResult
     *
     * @param source     源PageResult
     * @param targetType 目标类
     * @param peek       转换对象时执行
     * @param <T>        输入类型
     * @param <R>        输出类型
     */
    public static <T, R> PageResult<R> toBean(PageResult<T> source, Class<R> targetType, Consumer<R> peek) {
        if (source == null) {
            return null;
        }
        List<R> list = BeanUtils.toBean(source.getRows(), targetType);
        if (peek != null) {
            list.forEach(peek);
        }

        PageResult<R> pageResult = new PageResult<>();
        BeanUtil.copyProperties(source, pageResult);
        pageResult.setRows(list);
        return pageResult;
    }

}
