package cn.ibenbeni.bens.config.modular.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.modular.entity.SysConfigDO;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigPageReq;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigSaveReq;
import cn.ibenbeni.bens.config.modular.pojo.response.ConfigResp;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
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
 * 参数配置控制器
 *
 * @author: benben
 * @time: 2025/6/19 下午9:52
 */
@Tag(name = "管理后台 - 参数配置")
@RestController
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @Operation(summary = "创建参数配置")
    @PostResource(path = "/system/config/create")
    public ResponseData<Long> createConfig(@RequestBody @Validated SysConfigSaveReq req) {
        return new SuccessResponseData<>(sysConfigService.createConfig(req));
    }

    @Operation(summary = "删除参数配置")
    @Parameter(name = "id", description = "参数ID", required = true, example = "10")
    @DeleteResource(path = "/system/config/delete")
    public ResponseData<Boolean> deleteConfig(@RequestParam("id") Long id) {
        sysConfigService.deleteConfig(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除参数配置")
    @Parameter(name = "ids", description = "参数ID列表", required = true)
    @DeleteResource(path = "/system/config/delete-list")
    public ResponseData<Boolean> deleteConfigList(@RequestParam("ids") Set<Long> ids) {
        sysConfigService.deleteConfigList(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "修改参数配置")
    @PutResource(path = "/system/config/update")
    public ResponseData<Boolean> updateConfig(@RequestBody @Validated SysConfigSaveReq req) {
        sysConfigService.updateConfig(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取参数配置")
    @Parameter(name = "id", description = "参数ID", required = true, example = "10")
    @GetResource(path = "/system/config/get")
    public ResponseData<ConfigResp> getConfig(@RequestParam("id") Long id) {
        SysConfigDO config = sysConfigService.getConfig(id);
        return new SuccessResponseData<>(BeanUtil.toBean(config, ConfigResp.class));
    }

    @Operation(summary = "根据参数键名查询参数值", description = "不可见的配置,不允许返回给前端")
    @Parameter(name = "code", description = "参数编码", required = true, example = "sys_captcha_open")
    @GetResource(path = "/system/config/get-value-by-code")
    public ResponseData<String> getConfigByCode(@RequestParam("code") String code) {
        SysConfigDO config = sysConfigService.getConfigByCode(code);
        if (config == null) {
            return new SuccessResponseData<>(null);
        }
        if (!config.getVisibleFlag()) {
            throw new ConfigException(ConfigExceptionEnum.CONFIG_GET_VALUE_ERROR_IF_VISIBLE);
        }
        return new SuccessResponseData<>(config.getConfigValue());
    }

    @Operation(summary = "获取参数配置分页")
    @GetResource(path = "/system/config/page")
    public ResponseData<PageResult<ConfigResp>> getConfigPage(@Valid SysConfigPageReq req) {
        PageResult<SysConfigDO> configPage = sysConfigService.getConfigPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(configPage, ConfigResp.class));
    }

    @Operation(summary = "导出参数配置")
    @GetResource(path = "/system/config/export-excel")
    public void exportConfig(HttpServletResponse response, SysConfigPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysConfigDO> rows = sysConfigService.getConfigPage(req).getRows();
        ExcelUtils.write(
                response,
                "参数配置.xls",
                "数据",
                ConfigResp.class,
                BeanUtils.toBean(rows, ConfigResp.class)
        );
    }

}
