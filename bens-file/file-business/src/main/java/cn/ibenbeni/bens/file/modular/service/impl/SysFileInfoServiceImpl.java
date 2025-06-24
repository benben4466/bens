package cn.ibenbeni.bens.file.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.file.api.FileOperatorApi;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.api.exception.enums.FileExceptionEnum;
import cn.ibenbeni.bens.file.api.pojo.request.SysFileInfoRequest;
import cn.ibenbeni.bens.file.api.pojo.response.SysFileInfoResponse;
import cn.ibenbeni.bens.file.api.util.DownloadUtil;
import cn.ibenbeni.bens.file.api.util.PicFileTypeUtil;
import cn.ibenbeni.bens.file.modular.entity.SysFileInfo;
import cn.ibenbeni.bens.file.modular.factory.FileInfoFactory;
import cn.ibenbeni.bens.file.modular.mapper.SysFileInfoMapper;
import cn.ibenbeni.bens.file.modular.service.SysFileInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件信息服务实现类
 *
 * @author: benben
 * @time: 2025/6/20 下午5:02
 */
@Slf4j
@Service
public class SysFileInfoServiceImpl extends ServiceImpl<SysFileInfoMapper, SysFileInfo> implements SysFileInfoService {

    @Resource
    private FileOperatorApi fileOperatorApi;

    @Override
    public SysFileInfoResponse getFileInfoResult(Long fileId) {
        SysFileInfo dbSysFileInfo = this.querySysFileInfo(fileId);

        byte[] fileBytes;
        try {
            fileBytes = fileOperatorApi.getFileBytes(dbSysFileInfo.getFileBucket(), dbSysFileInfo.getFileObjectName());
        } catch (Exception ex) {
            log.error("获取文件流异常，具体信息为：{}", ex.getMessage());
            throw new FileException(FileExceptionEnum.FILE_STREAM_ERROR, ex.getMessage());
        }

        SysFileInfoResponse sysFileInfoResp = BeanUtil.toBean(dbSysFileInfo, SysFileInfoResponse.class);
        sysFileInfoResp.setFileBytes(fileBytes);
        return sysFileInfoResp;
    }

    @Override
    public List<SysFileInfoResponse> getFileInfoListByFileIds(String fileIds) {
        List<Long> fileIdList = Arrays.stream(fileIds.split(","))
                .map(item -> Long.valueOf(item.trim()))
                .collect(Collectors.toList());
        return this.getFileInfoListByFileIds(fileIdList);
    }

    @Override
    public List<SysFileInfoResponse> getFileInfoListByFileIds(List<Long> fileIdList) {
        if (CollUtil.isEmpty(fileIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<SysFileInfo> queryWrapper = Wrappers.lambdaQuery(SysFileInfo.class)
                .in(SysFileInfo::getFileId, fileIdList);
        List<SysFileInfo> list = this.list(queryWrapper);
        return list.stream()
                .map(item -> {
                    return BeanUtil.toBean(item, SysFileInfoResponse.class);
                })
                .collect(Collectors.toList());
    }

    @Override
    public SysFileInfo detail(SysFileInfoRequest sysFileInfoRequest) {
        return this.querySysFileInfo(sysFileInfoRequest.getFileId());
    }

    @Override
    public PageResult<SysFileInfo> page(SysFileInfoRequest sysFileInfoRequest) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = Wrappers.lambdaQuery(SysFileInfo.class)
                .like(StrUtil.isNotBlank(sysFileInfoRequest.getFileOriginName()), SysFileInfo::getFileOriginName, sysFileInfoRequest.getFileOriginName())
                .eq(ObjectUtil.isNotEmpty(sysFileInfoRequest.getFileLocation()), SysFileInfo::getFileLocation, sysFileInfoRequest.getFileLocation())
                .select(SysFileInfo::getFileId, SysFileInfo::getFileBucket, SysFileInfo::getFileObjectName,
                        SysFileInfo::getFileLocation, SysFileInfo::getFileOriginName, SysFileInfo::getFileSuffix,
                        BaseEntity::getCreateTime
                );

        Page<SysFileInfo> page = this.page(PageFactory.defaultPage(), queryWrapper);
        List<SysFileInfo> records = page.getRecords();

        // 排除默认头像
        List<SysFileInfo> newList = records.stream()
                .filter(item -> !FileConstants.DEFAULT_AVATAR_FILE_OBJ_NAME.equals(item.getFileOriginName()))
                .map(item -> {
                    // 若是图片，则设置URL地址，让其可以直接预览
                    if (PicFileTypeUtil.getFileImgTypeFlag(item.getFileOriginName())) {
                        item.setFileUrl("");
                    }
                    return item;
                })
                .collect(Collectors.toList());

        return PageResultFactory.createPageResult(page.setRecords(newList));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysFileInfoResponse uploadFile(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {
        // 文件上传请求转换为数据库响应字段
        SysFileInfo sysFileInfo = FileInfoFactory.createSysFileInfo(file, sysFileInfoRequest);

        // 存储文件
        this.storageFile(file, sysFileInfo);

        // 保存文件信息
        this.save(sysFileInfo);

        // 返回文件响应体
        return this.getFileInfoResponse(sysFileInfo);
    }

    @Override
    public void downloadFile(SysFileInfoRequest sysFileInfoRequest, HttpServletResponse response) {
        // 根据文件ID获取文件信息响应
        SysFileInfoResponse sysFileInfoResponse = this.getFileInfoResult(sysFileInfoRequest.getFileId());
        DownloadUtil.download(sysFileInfoResponse.getFileOriginName(), sysFileInfoResponse.getFileBytes(), response);
    }

    @Override
    public void storageFile(MultipartFile file, SysFileInfo sysFileInfo) {
        try {
            byte[] bytes = file.getBytes();
            fileOperatorApi.storageFile(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName(), bytes);
        } catch (Exception ex) {
            throw new FileException(FileExceptionEnum.ERROR_FILE, ex.getMessage());
        }
    }

    @Override
    public SysFileInfoResponse getFileInfoResponse(SysFileInfo sysFileInfo) {
        SysFileInfoResponse fileUploadInfoResp = BeanUtil.toBean(sysFileInfo, SysFileInfoResponse.class);
        String fileUrl = fileOperatorApi.getFileUrl(sysFileInfo.getFileBucket(), sysFileInfo.getFileObjectName());
        fileUploadInfoResp.setFileUrl(fileUrl);
        return fileUploadInfoResp;
    }

    @Override
    public String getFileUrl(Long fileId) {
        SysFileInfo dbFileInfo = this.querySysFileInfo(fileId);
        return fileOperatorApi.getFileUrl(dbFileInfo.getFileBucket(), dbFileInfo.getFileObjectName());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteReally(SysFileInfoRequest sysFileInfoRequest) {
        LambdaQueryWrapper<SysFileInfo> queryWrapper = Wrappers.lambdaQuery(SysFileInfo.class)
                .eq(SysFileInfo::getFileId, sysFileInfoRequest.getFileId());
        SysFileInfo dbFileInfo = this.getOne(queryWrapper, false);
        if (dbFileInfo == null) {
            return;
        }
        // 删除数据库记录
        this.remove(queryWrapper);

        // 删除文件
        fileOperatorApi.deleteFile(dbFileInfo.getFileBucket(), dbFileInfo.getFileObjectName());
    }

    private SysFileInfo querySysFileInfo(Long fileInfoId) {
        SysFileInfo sysFileInfo = this.getById(fileInfoId);
        if (sysFileInfo == null) {
            throw new FileException(FileExceptionEnum.NOT_EXISTED, fileInfoId);
        }
        return sysFileInfo;
    }

}
