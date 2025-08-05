package cn.ibenbeni.bens.log.operatelog.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogCreateReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogPageReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.response.OperateLogRespDTO;
import cn.ibenbeni.bens.log.operatelog.entity.OperateLogDO;
import cn.ibenbeni.bens.log.operatelog.pojo.request.OperateLogPageReq;
import cn.ibenbeni.bens.log.operatelog.pojo.response.OperateLogResp;
import cn.ibenbeni.bens.log.operatelog.service.OperateLogService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "管理后台 - 操作日志")
@RestController
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;

    @Operation(summary = "记录操作日志(测试)")
    @PostResource(path = "/system/operate-log/create")
    public ResponseData<Boolean> createOperateLog(@RequestBody @Valid OperateLogCreateReqDTO reqDTO) {
        operateLogService.createOperateLog(reqDTO);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "查看操作日志分页列表(测试,DTO)")
    @GetResource(path = "/system/operate-log/page-test")
    public ResponseData<PageResult<OperateLogResp>> pageOperateLog(@Valid OperateLogPageReqDTO req) {
        PageResult<OperateLogRespDTO> operateLogPage = operateLogService.getOperateLogPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(operateLogPage, OperateLogResp.class));
    }

    @Operation(summary = "查看操作日志分页列表")
    @GetResource(path = "/system/operate-log/page")
    public ResponseData<PageResult<OperateLogResp>> pageOperateLog(@Valid OperateLogPageReq req) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(operateLogPage, OperateLogResp.class));
    }

    @Operation(summary = "导出操作日志")
    @GetResource(path = "/system/operate-log/export-excel")
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperateLogDO> rows = operateLogService.getOperateLogPage(req).getRows();
        ExcelUtils.write(
                response,
                "操作日志.xlsx",
                "数据列表",
                OperateLogResp.class,
                BeanUtils.toBean(rows, OperateLogResp.class)
        );
    }

}
