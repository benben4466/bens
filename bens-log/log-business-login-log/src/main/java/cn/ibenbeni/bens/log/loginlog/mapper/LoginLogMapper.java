package cn.ibenbeni.bens.log.loginlog.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.log.loginlog.entity.LoginLogDO;
import cn.ibenbeni.bens.log.loginlog.pojo.request.LoginLogPageReq;

/**
 * 登陆日志-Mapper
 */
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {

    default PageResult<LoginLogDO> selectPage(LoginLogPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<LoginLogDO>()
                .likeIfPresent(LoginLogDO::getUserAccount, req.getUserAccount())
                .likeIfPresent(LoginLogDO::getLoginIp, req.getLoginIp())
                .betweenIfPresent(LoginLogDO::getCreateTime, req.getCreateTime())
                .orderByDesc(LoginLogDO::getLlgId)
        );
    }

}
