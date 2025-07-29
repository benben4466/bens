package cn.ibenbeni.bens.config.modular.service;

import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.modular.entity.SysConfigTypeDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypePageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeSaveReq;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 参数配置类型服务类
 *
 * @author: benben
 * @time: 2025/6/18 下午10:33
 */
public interface SysConfigTypeService extends IService<SysConfigTypeDO> {

    /**
     * 创建参数配置类型
     *
     * @return 参数配置类型ID
     */
    Long createConfigType(SysConfigTypeSaveReq req);

    /**
     * 删除参数配置类型
     *
     * @param configTypeId 参数配置类型ID
     */
    void deleteConfigType(Long configTypeId);

    /**
     * 批量删除参数配置类型
     *
     * @param configTypeIdSet 参数配置类型ID集合
     */
    void deleteConfigType(Set<Long> configTypeIdSet);

    /**
     * 更新参数配置类型
     */
    void updateConfigType(SysConfigTypeSaveReq req);

    /**
     * 查询参数配置类型
     *
     * @param configTypeId 参数配置类型ID
     */
    SysConfigTypeDO getConfigType(Long configTypeId);

    /**
     * 根据参数配置类型编码查询参数配置类型
     *
     * @param configTypeCode 参数配置类型编码
     */
    SysConfigTypeDO getConfigTypeByCode(String configTypeCode);

    /**
     * 分页查询参数配置类型
     */
    PageResult<SysConfigTypeDO> getConfigTypePage(SysConfigTypePageReq req);

    /**
     * 校验参数配置类型是否存在
     *
     * @param configTypeCode 参数配置类型编码
     */
    SysConfigTypeDO validateConfigTypeExists(String configTypeCode) throws ConfigException;

}
