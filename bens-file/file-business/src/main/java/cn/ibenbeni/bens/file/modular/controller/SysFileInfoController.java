package cn.ibenbeni.bens.file.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.pojo.request.SysFileInfoRequest;
import cn.ibenbeni.bens.file.api.pojo.response.SysFileInfoResponse;
import cn.ibenbeni.bens.file.modular.entity.SysFileInfo;
import cn.ibenbeni.bens.file.modular.service.SysFileInfoService;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.HttpServletUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息控制器
 *
 * @author: benben
 * @time: 2025/6/23 下午4:48
 */
@RestController
public class SysFileInfoController {

    @Resource
    private SysFileInfoService sysFileInfoService;

    /**
     * 上传文件
     */
    @PostMapping("/sysFileInfo/upload")
    public ResponseData<SysFileInfoResponse> upload(@RequestPart("file") MultipartFile file, @Validated SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfoResponse fileUploadInfoResult = this.sysFileInfoService.uploadFile(file, sysFileInfoRequest);
        return new SuccessResponseData<>(fileUploadInfoResult);
    }

    /**
     * 文件下载
     */
    @GetMapping("/sysFileInfo/download")
    public void privateDownload(@Validated SysFileInfoRequest sysFileInfoRequest) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        this.sysFileInfoService.downloadFile(sysFileInfoRequest, response);
    }

    /**
     * 根据附件ids查询附件信息
     * <p>分隔符: ,</p>
     *
     * @param fileIds 文件ID列表字符串
     */
    @GetMapping("/sysFileInfo/getFileInfoListByFileIds")
    public ResponseData<List<SysFileInfoResponse>> getFileInfoListByFileIds(@RequestParam(value = "fileIds") String fileIds) {
        List<SysFileInfoResponse> list = this.sysFileInfoService.getFileInfoListByFileIds(fileIds);
        return new SuccessResponseData<>(list);
    }

    /**
     * 根据附件ids查询附件信息
     */
    @PostMapping("/sysFileInfo/getFileInfoListByFileIds")
    public ResponseData<List<SysFileInfoResponse>> getFileInfoListByFileIds(@RequestBody SysFileInfoRequest sysFileInfoRequest) {
        List<SysFileInfoResponse> list = this.sysFileInfoService.getFileInfoListByFileIds(sysFileInfoRequest.getFileIdList());
        return new SuccessResponseData<>(list);
    }

    /**
     * 删除文件（不可逆）
     */
    @PostMapping("/sysFileInfo/deleteReally")
    public ResponseData<?> deleteReally(@RequestBody @Validated(SysFileInfoRequest.delete.class) SysFileInfoRequest sysFileInfoRequest) {
        this.sysFileInfoService.deleteReally(sysFileInfoRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查询文件信息列表（分页）
     */
    @GetMapping("/sysFileInfo/page")
    public ResponseData<PageResult<SysFileInfo>> fileInfoListPage(SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData<>(this.sysFileInfoService.page(sysFileInfoRequest));
    }

    /**
     * 查询详情文件信息
     */
    @GetMapping("/sysFileInfo/detail")
    public ResponseData<SysFileInfo> detail(@Validated SysFileInfoRequest sysFileInfoRequest) {
        return new SuccessResponseData<>(sysFileInfoService.detail(sysFileInfoRequest));
    }

}
