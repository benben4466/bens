package cn.ibenbeni.bens.config.modular.mapper;

import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigPageReq;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaUpdateWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;

import java.util.List;
import java.util.Set;

/**
 * 参数配置Mapper接口
 *
 * @author: benben
 * @time: 2025/6/18 上午10:30
 */
public interface SysConfigMapper extends BaseMapperX<SysConfigDO> {

    /**
     * 根据参数编码修改参数值
     *
     * @param configCode  参数编码
     * @param configValue 参数值
     * @return 影响行数
     */
    default int updateValueByCode(String configCode, String configValue) {
        return update(new LambdaUpdateWrapperX<SysConfigDO>()
                .eq(SysConfigDO::getConfigCode, configCode)
                .set(SysConfigDO::getConfigValue, configValue)
        );
    }

    /**
     * 获取系统初始化标识
     */
    default Boolean getInitConfigFlag() {
        SysConfigDO config = selectOne(SysConfigDO::getConfigCode, ConfigConstants.SYSTEM_CONFIG_INIT_FLAG_CODE);
        return Boolean.parseBoolean(config.getConfigValue());
    }

    default SysConfigDO getConfigByCode(String configCode) {
        return selectOne(SysConfigDO::getConfigCode, configCode);
    }

    default long countByConfigTypeCode(String configTypeCode) {
        return selectCount(new LambdaQueryWrapperX<SysConfigDO>()
                .eq(SysConfigDO::getConfigTypeCode, configTypeCode)
        );
    }

    default List<SysConfigDO> listByConfigTypeCode(Set<String> configTypeCode) {
        return selectList(new LambdaQueryWrapperX<SysConfigDO>()
                .in(SysConfigDO::getConfigTypeCode, configTypeCode)
        );
    }

    default PageResult<SysConfigDO> selectPage(SysConfigPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysConfigDO>()
                .likeIfPresent(SysConfigDO::getConfigName, req.getConfigName())
                .likeIfPresent(SysConfigDO::getConfigCode, req.getConfigCode())
                .eqIfPresent(SysConfigDO::getConfigType, req.getConfigType())
                .betweenIfPresent(SysConfigDO::getCreateTime, req.getCreateTime())
                .orderByAsc(SysConfigDO::getConfigSort)
        );
    }

}
