package cn.ibenbeni.bens.config.modular.service;

import cn.ibenbeni.bens.config.api.ConfigApi;
import cn.ibenbeni.bens.config.modular.entity.SysConfig;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigRequest;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 参数配置服务类
 *
 * @author: benben
 * @time: 2025/6/18 上午10:31
 */
public interface SysConfigService extends IService<SysConfig>, ConfigApi {

    /**
     * 新增
     */
    void add(SysConfigRequest sysConfigRequest);

    /**
     * 删除
     */
    void del(SysConfigRequest sysConfigRequest);

    /**
     * 批量删除
     */
    void batchDelete(SysConfigRequest sysConfigRequest);

    /**
     * 删除参数配置类型下所有参数配置
     */
    void delByConfigTypeCode(String configTypeCode);

    /**
     * 编辑
     */
    void edit(SysConfigRequest sysConfigRequest);

    /**
     * 查询详情
     */
    SysConfig detail(SysConfigRequest sysConfigRequest);

    /**
     * 查询列表
     */
    List<SysConfig> findList(SysConfigRequest sysConfigRequest);

    /**
     * 查询列表（分页）
     */
    PageResult<SysConfig> findPage(SysConfigRequest sysConfigRequest);

    /**
     * 根据参数配置编码获取参数配置值
     */
    String getConfigValueByConfigCode(String configCode);

}
