package cn.ibenbeni.bens.config.modular.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.config.modular.entity.SysConfigTypeDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypePageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeSaveReq;
import cn.ibenbeni.bens.config.modular.pojo.response.ConfigTypeResp;
import cn.ibenbeni.bens.config.modular.service.SysConfigTypeService;
import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 参数配置类型控制器
 *
 * @author: benben
 * @time: 2025/6/19 上午11:18
 */
@Tag(name = "管理后台 - 参数类型")
@RestController
public class SysConfigTypeController {

    @Resource
    private SysConfigTypeService configTypeService;

    @Operation(summary = "创建参数类型")
    @PostResource(path = "/system/config-type/create")
    public ResponseData<Long> createConfigType(@RequestBody @Validated SysConfigTypeSaveReq req) {
        return new SuccessResponseData<>(configTypeService.createConfigType(req));
    }

    @Operation(summary = "删除参数类型")
    @Parameter(name = "id", description = "参数类型ID", required = true, example = "10")
    @DeleteResource(path = "/system/config-type/delete")
    public ResponseData<Boolean> deleteConfig(@RequestParam("id") Long id) {
        configTypeService.deleteConfigType(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除参数类型")
    @Parameter(name = "ids", description = "参数类型ID列表", required = true)
    @DeleteResource(path = "/system/config-type/delete-list")
    public ResponseData<Boolean> deleteConfigList(@RequestParam("ids") Set<Long> ids) {
        configTypeService.deleteConfigType(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "修改参数类型")
    @PutResource(path = "/system/config-type/update")
    public ResponseData<Boolean> updateConfig(@RequestBody @Validated SysConfigTypeSaveReq req) {
        configTypeService.updateConfigType(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取参数类型")
    @Parameter(name = "id", description = "参数类型ID", required = true, example = "10")
    @GetResource(path = "/system/config-type/get")
    public ResponseData<ConfigTypeResp> getConfig(@RequestParam("id") Long id) {
        SysConfigTypeDO config = configTypeService.getConfigType(id);
        return new SuccessResponseData<>(BeanUtil.toBean(config, ConfigTypeResp.class));
    }

    @Operation(summary = "获取参数类型分页")
    @GetResource(path = "/system/config-type/page")
    public ResponseData<PageResult<ConfigTypeResp>> getConfigPage(@Valid SysConfigTypePageReq req) {
        PageResult<SysConfigTypeDO> configTypePage = configTypeService.getConfigTypePage(req);
        return new SuccessResponseData<>(DbUtil.toBean(configTypePage, ConfigTypeResp.class));
    }

    @Operation(summary = "导出参数类型")
    @GetResource(path = "/system/config-type/export-excel")
    public void exportConfig(HttpServletResponse response, SysConfigTypePageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysConfigTypeDO> rows = configTypeService.getConfigTypePage(req).getRows();
        ExcelUtils.write(
                response,
                "参数配置.xls",
                "数据",
                ConfigTypeResp.class,
                BeanUtils.toBean(rows, ConfigTypeResp.class)
        );
    }

}
