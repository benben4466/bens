package cn.ibenbeni.bens.file.modular.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.file.api.util.FileTypeUtils;
import cn.ibenbeni.bens.file.modular.entity.FileConfigDO;
import cn.ibenbeni.bens.file.modular.entity.FileDO;
import cn.ibenbeni.bens.file.modular.pojo.request.FileCreateReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FilePageReq;
import cn.ibenbeni.bens.file.modular.pojo.request.FileUploadReq;
import cn.ibenbeni.bens.file.modular.pojo.response.FilePreSignatureUrlResp;
import cn.ibenbeni.bens.file.modular.pojo.response.FileResp;
import cn.ibenbeni.bens.file.modular.service.FileConfigService;
import cn.ibenbeni.bens.file.modular.service.FileService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Slf4j
@Tag(name = "管理后台 - 文件存储")
@RestController
public class FileController {

    @Resource
    private FileConfigService fileConfigService;

    @Resource
    private FileService fileService;

    @Operation(summary = "上传文件", description = "模式一:用于后端上传文件")
    @PostResource(path = "/system/file/upload")
    public ResponseData<String> uploadFile(FileUploadReq req) throws IOException {
        MultipartFile file = req.getFile();
        byte[] fileContent = IoUtil.readBytes(file.getInputStream());
        String fileUrl = fileService.createFile(fileContent, file.getOriginalFilename(), req.getDirectory(), file.getContentType());
        return new SuccessResponseData<>(fileUrl);
    }

    @Operation(summary = "创建文件", description = "模式二:前端上传文件,配合pre-signature-url接口,记录上传了上传的文件")
    @PostResource(path = "/system/file/create")
    public ResponseData<Long> createFile(@RequestBody @Valid FileCreateReq req) {
        return new SuccessResponseData<>(fileService.createFile(req));
    }

    @Operation(summary = "删除文件")
    @Parameter(name = "id", description = "文件ID", required = true)
    @DeleteResource(path = "/system/file/delete")
    public ResponseData<Boolean> deleteFile(@RequestParam("id") Long id) throws Exception {
        fileService.deleteFile(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除文件")
    @Parameter(name = "ids", description = "文件ID集合", required = true)
    @DeleteResource(path = "/system/file/delete-list")
    public ResponseData<Boolean> deleteFile(@RequestParam("ids") Set<Long> ids) throws Exception {
        fileService.deleteFile(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "下载文件")
    @Parameter(name = "configId", description = "文件配置ID", required = true)
    @GetResource(path = "/system/file/{configId}/get/**")
    public void getFileContent(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("configId") Long configId) throws Exception {
        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (StrUtil.isEmpty(path)) {
            throw new IllegalArgumentException("结尾的path路径必须传递");
        }
        // 解决中文路径问题
        path = URLUtil.decode(path);

        FileConfigDO fileConfig = fileConfigService.validateFileConfigExists(configId);
        // 读取内容
        byte[] content = fileService.getFileContent(fileConfig.getFileConfigCode(), path);
        if (content == null) {
            log.warn("[getFileContent][fileConfigCode({}),path({})文件不存在]", fileConfig.getFileConfigCode(), path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        // 返回文件
        FileTypeUtils.writeAttachment(response, path, content);
    }

    @Operation(summary = "获取文件预签名地址", description = "模式二:前端上传文件：用于前端直接上传七牛、阿里云OSS等文件存储器")
    @Parameters({
            @Parameter(name = "name", description = "文件名称", required = true),
            @Parameter(name = "directory", description = "文件目录")
    })
    @GetResource(path = "/system/file/pre-signature-url")
    public ResponseData<FilePreSignatureUrlResp> getFilePreSignatureUrl(
            @RequestParam("name") String name,
            @RequestParam(value = "directory", required = false) String directory) {
        return new SuccessResponseData<>(fileService.getPreSignatureFileUrl(name, directory));
    }

    @Operation(summary = "获得文件分页列表")
    @GetResource(path = "/system/file/page")
    public ResponseData<PageResult<FileResp>> getFilePage(@Valid FilePageReq req) {
        PageResult<FileDO> filePage = fileService.getFilePage(req);
        return new SuccessResponseData<>(DbUtil.toBean(filePage, FileResp.class));
    }

}
