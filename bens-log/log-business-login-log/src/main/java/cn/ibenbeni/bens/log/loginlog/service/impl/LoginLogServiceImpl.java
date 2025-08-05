package cn.ibenbeni.bens.log.loginlog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.log.api.pojo.dto.request.LoginLogCreateReqDTO;
import cn.ibenbeni.bens.log.loginlog.entity.LoginLogDO;
import cn.ibenbeni.bens.log.loginlog.mapper.LoginLogMapper;
import cn.ibenbeni.bens.log.loginlog.pojo.request.LoginLogPageReq;
import cn.ibenbeni.bens.log.loginlog.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登陆日志-服务实现类
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {

    // region 属性

    @Resource
    private LoginLogMapper loginLogMapper;

    // endregion

    // region 公共方法

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReq req) {
        return loginLogMapper.selectPage(req);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO req) {
        LoginLogDO loginLog = BeanUtil.toBean(req, LoginLogDO.class);
        save(loginLog);
    }

    // endregion

    // region 私有方法
    // endregion

}
