package cn.ibenbeni.bens.file.modular.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.file.modular.entity.FileConfigDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigPageReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FileConfigSaveReq;
import cn.ibenbeni.bens.file.modular.pojo.response.FileConfigResp;
import cn.ibenbeni.bens.file.modular.service.FileConfigService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - 文件配置")
@RestController
public class FileConfigController {

    @Resource
    private FileConfigService fileConfigService;

    @Operation(summary = "创建文件配置")
    @PostResource(path = "/system/file-config/create")
    public ResponseData<Long> createFileConfig(@RequestBody @Valid FileConfigSaveReq req) {
        return new SuccessResponseData<>(fileConfigService.createFileConfig(req));
    }

    @Operation(summary = "删除文件配置")
    @Parameter(name = "id", description = "文件配置ID", required = true, example = "10")
    @DeleteResource(path = "/system/file-config/delete")
    public ResponseData<Boolean> deleteFileConfig(@RequestParam("id") Long id) {
        fileConfigService.deleteFileConfig(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除文件配置")
    @Parameter(name = "ids", description = "文件配置ID集合", required = true)
    @DeleteResource(path = "/system/file-config/delete-list")
    public ResponseData<Boolean> deleteFileConfig(@RequestParam("ids") Set<Long> ids) {
        fileConfigService.deleteFileConfig(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新文件配置")
    @PutResource(path = "/system/file-config/update")
    public ResponseData<Boolean> updateFileConfig(@RequestBody @Valid FileConfigSaveReq req) {
        fileConfigService.updateFileConfig(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新文件配置为Master")
    @Parameter(name = "id", description = "文件配置ID", required = true, example = "10")
    @PutResource(path = "/system/file-config/update-master")
    public ResponseData<Boolean> updateFileConfigMaster(@RequestParam("id") Long id) {
        fileConfigService.updateFileConfigMaster(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得文件配置")
    @Parameter(name = "id", description = "文件配置ID", required = true, example = "10")
    @GetResource(path = "/system/file-config/get")
    public ResponseData<FileConfigResp> getFileConfig(@RequestParam("id") Long id) {
        FileConfigDO fileConfig = fileConfigService.getFileConfig(id);
        return new SuccessResponseData<>(BeanUtil.toBean(fileConfig, FileConfigResp.class));
    }

    @Operation(summary = "获得文件配置分页列表")
    @GetResource(path = "/system/file-config/page")
    public ResponseData<PageResult<FileConfigResp>> getFileConfigPage(@Valid FileConfigPageReq req) {
        PageResult<FileConfigDO> pageResult = fileConfigService.getFileConfigPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(pageResult, FileConfigResp.class));
    }

    @Operation(summary = "测试文件配置是否正确")
    @GetResource(path = "/system/file-config/test")
    public ResponseData<String> testFileConfig(@RequestParam("id") Long id) throws Exception {
        return new SuccessResponseData<>(fileConfigService.testFileConfig(id));
    }

}
