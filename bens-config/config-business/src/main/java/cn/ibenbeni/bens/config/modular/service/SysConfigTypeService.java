package cn.ibenbeni.bens.config.modular.service;

import cn.ibenbeni.bens.config.modular.entity.SysConfigType;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeRequest;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 参数配置类型服务类
 *
 * @author: benben
 * @time: 2025/6/18 下午10:33
 */
public interface SysConfigTypeService extends IService<SysConfigType> {

    /**
     * 新增
     */
    void add(SysConfigTypeRequest sysConfigTypeRequest);

    /**
     * 删除
     */
    void del(SysConfigTypeRequest sysConfigTypeRequest);

    /**
     * 编辑
     */
    void edit(SysConfigTypeRequest sysConfigTypeRequest);

    /**
     * 查询详情
     */
    SysConfigType detail(SysConfigTypeRequest sysConfigTypeRequest);

    /**
     * 查询列表
     */
    List<SysConfigType> findList(SysConfigTypeRequest sysConfigTypeRequest);

    /**
     * 查询列表（分页）
     */
    PageResult<SysConfigType> findPage(SysConfigTypeRequest sysConfigTypeRequest);

}
