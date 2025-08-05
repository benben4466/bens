package cn.ibenbeni.bens.log.operatelog.service;

import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.log.api.OperateLogServiceApi;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogCreateReqDTO;
import cn.ibenbeni.bens.rule.enums.user.UserTypeEnum;
import cn.ibenbeni.bens.rule.util.ServletUtils;
import cn.ibenbeni.bens.rule.util.TracerUtils;
import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * bizlog 组件的扩展接口 ILogRecordService 的实现类
 * <p>基于{@link cn.ibenbeni.bens.log.api.OperateLogServiceApi}实现记录日志操作</p>
 */
@Slf4j
public class DefaultLogRecordServiceImpl implements ILogRecordService {

    @Resource
    private LoginUserApi loginUserApi;

    @Resource
    private OperateLogServiceApi operateLogServiceApi;

    // region 实现方法

    @Override
    public void record(LogRecord logRecord) {
        OperateLogCreateReqDTO reqDTO = new OperateLogCreateReqDTO();
        try {
            // 链路追踪ID
            reqDTO.setTraceId(TracerUtils.getTraceId());
            // 填充用户信息
            fillUserFields(reqDTO);
            // 填充模块信息
            fillModuleFields(reqDTO, logRecord);
            // 填充请求信息
            fillRequestFields(reqDTO);

            reqDTO.setServerIp(ServletUtils.getServerIp());

            // 异步记录日志
            operateLogServiceApi.createOperateLogAsync(reqDTO);
        } catch (Throwable throwable) {
            log.error("[record][url({})][log({})]发生异常", reqDTO.getRequestUrl(), reqDTO, throwable);
        }
    }

    @Override
    public List<LogRecord> queryLog(String bizNo, String type) {
        throw new UnsupportedOperationException("使用OperateLogServiceApi进行操作日志的查询");
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        throw new UnsupportedOperationException("使用OperateLogServiceApi进行操作日志的查询");
    }

    // endregion

    // region 私有方法

    /**
     * 填充用户信息
     */
    private void fillUserFields(OperateLogCreateReqDTO reqDTO) {
        // 获取登陆用户信息
        LoginUser loginUser = loginUserApi.getLoginUser();
        if (loginUser == null) {
            return;
        }
        reqDTO.setUserId(loginUser.getUserId());
        reqDTO.setUserAccount(loginUser.getAccount());
        // 默认为管理员
        reqDTO.setUserType(UserTypeEnum.ADMIN.getType());
    }

    /**
     * 填充模块信息
     */
    private void fillModuleFields(OperateLogCreateReqDTO reqDTO, LogRecord logRecord) {
        reqDTO.setModuleNo(logRecord.getType());
        reqDTO.setSubModuleNo(logRecord.getSubType());
        reqDTO.setBizId(Long.parseLong(logRecord.getBizNo()));
        reqDTO.setOpAction(logRecord.getAction());
        reqDTO.setExpandField(logRecord.getExtra());
    }

    /**
     * 填充请求信息
     */
    private void fillRequestFields(OperateLogCreateReqDTO reqDTO) {
        // 获得 Request 对象
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return;
        }
        reqDTO.setRequestMethod(request.getMethod());
        reqDTO.setRequestUrl(request.getRequestURI());
        reqDTO.setUserIp(ServletUtils.getClientIP(request));
        reqDTO.setUserAgent(ServletUtils.getUserAgent(request));
    }

    // endregion

}
