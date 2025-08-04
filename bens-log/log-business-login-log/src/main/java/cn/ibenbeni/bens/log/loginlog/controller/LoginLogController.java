package cn.ibenbeni.bens.log.loginlog.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.log.api.pojo.dto.LoginLogCreateReqDTO;
import cn.ibenbeni.bens.log.loginlog.entity.LoginLogDO;
import cn.ibenbeni.bens.log.loginlog.pojo.request.LoginLogPageReq;
import cn.ibenbeni.bens.log.loginlog.pojo.response.LoginLogResp;
import cn.ibenbeni.bens.log.loginlog.service.LoginLogService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "管理后台 - 登录日志")
@RestController
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @Operation(summary = "创建登录日志(测试用途)")
    @PostResource(path = "/system/login-log/create")
    public ResponseData<Boolean> createLoginLog(@Valid LoginLogCreateReqDTO req) {
        loginLogService.createLoginLog(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得登录日志分页列表")
    @GetResource(path = "/system/login-log/page")
    public ResponseData<PageResult<LoginLogResp>> getLoginLogPage(@Valid LoginLogPageReq req) {
        PageResult<LoginLogDO> loginLogPage = loginLogService.getLoginLogPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(loginLogPage, LoginLogResp.class));
    }

    @Operation(summary = "导出登录日志Excel")
    @GetResource(path = "/system/login-log/export-excel")
    public void exportLoginLog(HttpServletResponse response, @Valid LoginLogPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LoginLogDO> list = loginLogService.getLoginLogPage(req).getRows();
        ExcelUtils.write(
                response,
                "登录日志.xls",
                "数据列表",
                LoginLogResp.class,
                BeanUtils.toBean(list, LoginLogResp.class)
        );
    }

}
