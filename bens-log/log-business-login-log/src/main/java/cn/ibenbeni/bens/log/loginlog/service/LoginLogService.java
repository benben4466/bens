package cn.ibenbeni.bens.log.loginlog.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.log.api.LoginLogServiceApi;
import cn.ibenbeni.bens.log.loginlog.entity.LoginLogDO;
import cn.ibenbeni.bens.log.loginlog.pojo.request.LoginLogPageReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 登陆日志-服务类
 */
public interface LoginLogService extends IService<LoginLogDO>, LoginLogServiceApi {

    /**
     * 获取登陆日志分页列表
     */
    PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReq req);

}
