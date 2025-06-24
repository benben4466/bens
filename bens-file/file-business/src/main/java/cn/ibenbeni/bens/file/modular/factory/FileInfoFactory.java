package cn.ibenbeni.bens.file.modular.factory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.file.api.FileOperatorApi;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.file.api.expander.FileConfigExpander;
import cn.ibenbeni.bens.file.api.pojo.request.SysFileInfoRequest;
import cn.ibenbeni.bens.file.modular.entity.SysFileInfo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * 文件信息组装工厂
 *
 * @author: benben
 * @time: 2025/6/22 上午11:24
 */
public class FileInfoFactory {

    /**
     * 创建文件信息
     *
     * @param file               文件
     * @param sysFileInfoRequest 文件请求
     */
    public static SysFileInfo createSysFileInfo(MultipartFile file, SysFileInfoRequest sysFileInfoRequest) {
        SysFileInfo sysFileInfo = new SysFileInfo();

        // 填充基础的文件信息
        fillCommonInfo(sysFileInfo, sysFileInfoRequest);

        // 获取文件原始名称
        String originalFileName = file.getOriginalFilename();
        sysFileInfo.setFileOriginName(originalFileName);

        // 获取文件后缀（不包含点）
        String fileSuffix = null;
        if (StrUtil.isNotBlank(originalFileName)) {
            fileSuffix = StrUtil.subAfter(originalFileName, FileConstants.FILE_POSTFIX_SEPARATOR, true);
        }
        sysFileInfo.setFileSuffix(fileSuffix);

        // 计算文件大小KB
        long fileSizeKb = Convert.toLong(NumberUtil.div(new BigDecimal(file.getSize()), BigDecimal.valueOf(1024)).setScale(0, BigDecimal.ROUND_HALF_UP));
        sysFileInfo.setFileSizeKb(fileSizeKb);

        // 计算后的文件大小信息
        String readableFileSize = FileUtil.readableFileSize(file.getSize());
        sysFileInfo.setReadableFileSize(readableFileSize);

        // 生成存储桶中文件名称，格式：文件ID.后缀
        String storageFileName = sysFileInfo.getFileId() + FileConstants.FILE_POSTFIX_SEPARATOR + fileSuffix;
        sysFileInfo.setFileObjectName(storageFileName);

        return sysFileInfo;
    }

    /**
     * 填充公共信息
     */
    private static void fillCommonInfo(SysFileInfo sysFileInfo, SysFileInfoRequest sysFileInfoRequest) {
        // 文件唯一ID（文件信息ID）
        long fileId = IdWorker.getId();
        sysFileInfo.setFileId(fileId);

        // 文件存储位置
        FileOperatorApi fileOperatorApi = SpringUtil.getBean(FileOperatorApi.class);
        sysFileInfo.setFileLocation(fileOperatorApi.getFileLocationEnum().getCode());

        // 存储桶位置
        String fileBucket = FileConfigExpander.getDefaultBucket();
        if (StrUtil.isNotBlank(sysFileInfoRequest.getFileBucket())) {
            fileBucket = sysFileInfoRequest.getFileBucket();
        }
        sysFileInfo.setFileBucket(fileBucket);
    }

}
