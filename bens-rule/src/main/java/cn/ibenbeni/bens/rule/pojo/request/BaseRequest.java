package cn.ibenbeni.bens.rule.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求基类，所有接口请求可继承此类
 *
 * @author benben
 * @date 2025/4/19  下午1:36
 */
@Data
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询开始时间
     */
    private String searchBeginTime;

    /**
     * 查询结束时间
     */
    private String searchEndTime;

    /**
     * 分页：每页大小（默认20）
     */
    private Integer pageSize;

    /**
     * 分页：第几页（从1开始）
     */
    private Integer pageNo;

    /**
     * 唯一请求号
     */
    private String requestNo;

    /**
     * 业务节点id
     */
    private String spanId;

    /**
     * 当前登录用户的token
     */
    private String token;

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 新增
     */
    public interface add {
    }

    /**
     * 删除
     */
    public interface delete {
    }

    /**
     * 批量删除
     */
    public interface batchDelete {
    }

    /**
     * 编辑
     */
    public interface edit {
    }

    /**
     * 修改状态
     */
    public interface updateStatus {
    }

    /**
     * 查询详情
     */
    public interface detail {
    }

    /**
     * 查询所有
     */
    public interface list {
    }

    /**
     * 分页
     */
    public interface page {
    }

    /**
     * 导出
     */
    public interface export {
    }

    // endregion

}
