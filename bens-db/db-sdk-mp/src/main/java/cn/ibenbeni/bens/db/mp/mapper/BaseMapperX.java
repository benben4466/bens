package cn.ibenbeni.bens.db.mp.mapper;

import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.sort.SortingField;
import cn.ibenbeni.bens.db.api.util.MyBatisUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 在 MyBatis-Plus 的 BaseMapper 的基础上拓展，提供更多的能力
 * <p>
 * 1.{@link com.baomidou.mybatisplus.core.mapper.BaseMapper} 为 MyBatis-Plus 的基础接口，提供基础的 CRUD 能力
 * 2.{@link com.github.yulichang.base.MPJBaseMapper} 为 MyBatis-Plus-Join 的基础接口，提供连表 Join 能力
 *
 * @author: benben
 * @time: 2025/7/6 上午9:10
 */
public interface BaseMapperX<T> extends MPJBaseMapper<T> {

    /**
     * 根据实体字段条件，查询一条记录 (单参数相等字段查询)
     *
     * <p>SFunction: 专门用于表示单参数的函数式接口, 通常用于操作实体类的字段</p>
     * <p>使用SFunction可使用Lambda表达式, 如SysUserDO::getAccount</p>
     *
     * @param field 字段名称
     * @param value 字段值
     * @return 查询结果
     * @see SFunction T: 实体类类型；R: 字段类型
     */
    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 根据实体字段条件，查询多条记录 (单参数相等字段查询)
     *
     * <p>SFunction: 专门用于表示单参数的函数式接口, 通常用于操作实体类的字段</p>
     * <p>使用SFunction可使用Lambda表达式, 如SysUserDO::getAccount</p>
     *
     * @param field 字段名称
     * @param value 字段值
     * @return 查询结果集合
     * @see SFunction T: 实体类类型；R: 字段类型
     */
    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default PageResult<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        return this.selectPage(pageParam, null, queryWrapper);
    }

    default PageResult<T> selectPage(PageParam pageParam, Collection<SortingField> sortingFields, @Param("ew") Wrapper<T> queryWrapper) {
        // 特殊：不分页，直接查询全部
        if (PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())) {
            List<T> list = selectList(queryWrapper);
            PageResult<T> pageResult = new PageResult<>();
            pageResult.setRows(list);
            pageResult.setTotalRows(list.size());
            pageResult.setPageNo(PageParam.PAGE_SIZE_NONE);
            pageResult.setPageSize(PageParam.PAGE_SIZE_NONE);
            return pageResult;
        }

        // 分页查询
        Page<T> mpPage = MyBatisUtils.buildPage(pageParam, sortingFields);
        selectPage(mpPage, queryWrapper);

        // 转换返回
        return PageResultFactory.createPageResult(mpPage);
    }

}
