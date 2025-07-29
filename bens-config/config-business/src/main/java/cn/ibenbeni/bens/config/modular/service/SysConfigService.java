package cn.ibenbeni.bens.config.modular.service;

import cn.ibenbeni.bens.config.api.ConfigApi;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigPageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigSaveReq;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 参数配置服务类
 *
 * @author: benben
 * @time: 2025/6/18 上午10:31
 */
public interface SysConfigService extends IService<SysConfigDO>, ConfigApi {

    /**
     * 创建参数配置
     *
     * @return 参数ID
     */
    Long createConfig(SysConfigSaveReq req);

    /**
     * 删除参数配置
     *
     * @param configId 参数ID
     */
    void deleteConfig(Long configId);

    /**
     * 批量删除参数配置
     *
     * @param configIdSet 参数ID集合
     */
    void deleteConfigList(Set<Long> configIdSet);

    /**
     * 修改参数配置
     */
    void updateConfig(SysConfigSaveReq req);

    /**
     * 获取参数配置
     *
     * @param configId 参数ID
     */
    SysConfigDO getConfig(Long configId);

    /**
     * 根据参数编码获取参数配置
     *
     * @param configCode 参数编码
     */
    SysConfigDO getConfigByCode(String configCode);

    /**
     * 获取参数类型下，参数数量
     *
     * @param configTypeCode 参数类型编码
     */
    long countByConfigTypeCode(String configTypeCode);

    /**
     * 根据参数类型编码集合获取参数列表
     *
     * @param configTypeCode 参数类型编码集合
     */
    List<SysConfigDO> listByConfigTypeCode(Set<String> configTypeCode);

    /**
     * 获取参数配置分页
     */
    PageResult<SysConfigDO> getConfigPage(SysConfigPageReq req);

}
