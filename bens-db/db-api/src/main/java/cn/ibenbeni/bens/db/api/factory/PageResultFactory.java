package cn.ibenbeni.bens.db.api.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页的返回结果创建工厂
 * <p>般由mybatis-plus的Page对象转为PageResult</p>
 *
 * @author benben
 * @date 2025/4/18  下午12:39
 */
public class PageResultFactory {

    /**
     * 将mybatis-plus的page转成自定义的PageResult，扩展了totalPage总页数
     */
    public static <T> PageResult<T> createPageResult(Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRows(page.getRecords());
        pageResult.setTotalRows(Convert.toInt(page.getTotal()));
        pageResult.setPageNo(Convert.toInt(page.getCurrent()));
        pageResult.setPageSize(Convert.toInt(page.getSize()));
        pageResult.setTotalPage(
                PageUtil.totalPage(pageResult.getTotalRows(), pageResult.getPageSize()));
        return pageResult;
    }

    /**
     * 将mybatis-plus的page转成自定义的PageResult，扩展了totalPage总页数
     */
    public static <T> PageResult<T> createPageResult(List<T> rows, Long count, Integer pageSize, Integer pageNo) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRows(rows);
        pageResult.setTotalRows(Convert.toInt(count));
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalPage(PageUtil.totalPage(pageResult.getTotalRows(), pageSize));
        return pageResult;
    }

    /**
     * 创建一个空的分页结果
     */
    public static <T> PageResult<T> createEmptyPageResult() {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRows(new ArrayList<>());
        pageResult.setTotalRows(0);
        pageResult.setPageNo(1);
        pageResult.setPageSize(10);
        pageResult.setTotalPage(PageUtil.totalPage(pageResult.getTotalRows(), 10));
        return pageResult;
    }

}
