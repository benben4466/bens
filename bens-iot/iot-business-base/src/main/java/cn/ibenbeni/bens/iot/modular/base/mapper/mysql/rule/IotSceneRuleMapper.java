package cn.ibenbeni.bens.iot.modular.base.mapper.mysql.rule;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * IOT-场景联动规则-Mapper
 */
@Mapper
public interface IotSceneRuleMapper extends BaseMapperX<IotSceneRuleDO> {

    default List<IotSceneRuleDO> selectListByStatus(Integer statusFlag) {
        return selectList(new LambdaQueryWrapperX<IotSceneRuleDO>()
                .eqIfPresent(IotSceneRuleDO::getStatusFlag, statusFlag)
        );
    }

    default PageResult<IotSceneRuleDO> pageSceneRule(IotSceneRulePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<IotSceneRuleDO>()
                .likeIfPresent(IotSceneRuleDO::getName, pageReq.getName())
                .likeIfPresent(IotSceneRuleDO::getDescription, pageReq.getDescription())
                .eqIfPresent(IotSceneRuleDO::getStatusFlag, pageReq.getStatusFlag())
                .betweenIfPresent(IotSceneRuleDO::getCreateTime, pageReq.getCreateTime())
                .orderByDesc(IotSceneRuleDO::getId)
        );
    }

}
