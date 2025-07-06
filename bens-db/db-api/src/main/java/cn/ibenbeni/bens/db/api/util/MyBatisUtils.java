package cn.ibenbeni.bens.db.api.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.sort.SortingField;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MyBatis工具类
 *
 * @author: benben
 * @time: 2025/7/6 上午9:54
 */
public class MyBatisUtils {

    public static <T> Page<T> buildPage(PageParam pageParam, Collection<SortingField> sortingFields) {
        // 页码 + 数量
        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        // 排序字段
        // StrUtil.toUnderlineCase: 转换为下划线
        if (CollectionUtil.isNotEmpty(sortingFields)) {
            // OrderItem类: 排序规则类
            List<OrderItem> orderItems = sortingFields.stream()
                    .map(sortingField -> SortingField.ORDER_ASC.equals(sortingField.getOrder())
                            ? OrderItem.asc(StrUtil.toUnderlineCase(sortingField.getField()))
                            : OrderItem.desc(StrUtil.toUnderlineCase(sortingField.getField())))
                    .collect(Collectors.toList());
            // Page#addOrder: 添加排序字段
            page.addOrder(orderItems);
        }

        return page;
    }

}
