package cn.ibenbeni.bens.db.api.pojo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装
 *
 * @author benben
 * @date 2025/4/18  下午12:34
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 第几页
     */
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;

    /**
     * 总页数
     */
    private Integer totalPage = 0;

    /**
     * 总记录数
     */
    private Integer totalRows = 0;

    /**
     * 结果集
     */
    private List<T> rows;

}
