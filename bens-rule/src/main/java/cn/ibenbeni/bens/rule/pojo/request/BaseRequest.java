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

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 新增
     */
    public interface create {
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
     * 更新
     */
    public interface update {
    }

    /**
     * 查询详情
     */
    public interface get {
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
