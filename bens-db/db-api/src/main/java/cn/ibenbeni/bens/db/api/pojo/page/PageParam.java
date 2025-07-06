package cn.ibenbeni.bens.db.api.pojo.page;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author: benben
 * @time: 2025/7/3 下午10:53
 */
@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 每页条数 - 不分页
     *
     * 例如说，导出接口，可以设置 {@link #pageSize} 为 -1 不分页，查询所有数据。
     */
    public static final Integer PAGE_SIZE_NONE = -1;

    /**
     * 页码
     */
    private Integer pageNo = PAGE_NO;

    /**
     * 每页条数
     */
    private Integer pageSize = PAGE_SIZE;

}
