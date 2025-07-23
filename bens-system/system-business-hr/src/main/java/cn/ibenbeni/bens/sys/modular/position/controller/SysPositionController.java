package cn.ibenbeni.bens.sys.modular.position.controller;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.modular.position.entity.SysPositionDO;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionPageReq;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionSaveReq;
import cn.ibenbeni.bens.sys.modular.position.pojo.response.PositionResp;
import cn.ibenbeni.bens.sys.modular.position.pojo.response.PositionSimpleResp;
import cn.ibenbeni.bens.sys.modular.position.service.SysPositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 职位信息控制器
 *
 * @author: benben
 * @time: 2025/7/12 下午1:39
 */
@Tag(name = "管理后台 - 职位")
@RestController
public class SysPositionController {

    @Resource
    private SysPositionService sysPositionService;

    @Operation(summary = "创建职位")
    @PostMapping("/system/position/create")
    public ResponseData<Long> createPosition(@RequestBody @Validated PositionSaveReq req) {
        return new SuccessResponseData<>(sysPositionService.createPosition(req));
    }

    @Operation(summary = "删除职位")
    @Parameter(name = "id", description = "职位ID", required = true, example = "1")
    @DeleteMapping("/system/position/delete")
    public ResponseData<Boolean> deletePosition(@RequestParam("id") Long id) {
        sysPositionService.deletePosition(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新职位")
    @PutMapping("/system/position/update")
    public ResponseData<Boolean> updatePosition(@RequestBody @Validated PositionSaveReq req) {
        sysPositionService.updatePosition(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得职位信息")
    @Parameter(name = "id", description = "职位ID", required = true, example = "1")
    @GetMapping("/system/position/get")
    public ResponseData<PositionResp> getPosition(@RequestParam("id") Long id) {
        SysPositionDO position = sysPositionService.getPosition(id);
        return new SuccessResponseData<>(BeanUtils.toBean(position, PositionResp.class));
    }

    @Operation(summary = "获取职位全列表")
    @GetMapping("/system/position/simple-list")
    public ResponseData<List<PositionSimpleResp>> getSimplePositionList() {
        List<SysPositionDO> positionList = sysPositionService.getPositionList(null, CollUtil.set(false, StatusEnum.ENABLE.getCode()));
        return new SuccessResponseData<>(BeanUtils.toBean(positionList, PositionSimpleResp.class));
    }

    @Operation(summary = "获得岗位分页列表")
    @GetMapping("/system/position/page")
    public ResponseData<PageResult<PositionResp>> getPositionPage(PositionPageReq req) {
        PageResult<SysPositionDO> positionPage = sysPositionService.getPositionPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(positionPage, PositionResp.class));
    }

    @Operation(summary = "职位管理")
    @GetMapping("/system/position/export-excel")
    public void exportPosition(HttpServletResponse response, PositionPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE); // 不分页
        List<SysPositionDO> list = sysPositionService.getPositionPage(req).getRows();
        ExcelUtils.write(
                response,
                "职位数据.xls", "职位列表",
                PositionResp.class,
                BeanUtils.toBean(list, PositionResp.class)
        );
    }

}
