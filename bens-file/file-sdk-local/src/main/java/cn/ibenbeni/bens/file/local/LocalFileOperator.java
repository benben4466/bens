package cn.ibenbeni.bens.file.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.ibenbeni.bens.file.api.FileOperatorApi;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.file.api.enums.FileLocationEnum;
import cn.ibenbeni.bens.file.api.exception.FileException;
import cn.ibenbeni.bens.file.api.exception.enums.FileExceptionEnum;
import cn.ibenbeni.bens.file.api.expander.FileConfigExpander;
import cn.ibenbeni.bens.rule.util.HttpServletUtil;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.InputStream;

/**
 * 本地文件操作实现
 *
 * @author: benben
 * @time: 2025/6/22 下午4:28
 */
@NoArgsConstructor
public class LocalFileOperator implements FileOperatorApi {

    @Override
    public void initClient() {
    }

    @Override
    public void destroyClient() {
    }

    @Override
    public Object getClient() {
        return null;
    }

    @Override
    public void storageFile(String bucketName, String key, byte[] bytes) {
        // 存储路径
        String bucketPath = this.getCurrentSavePath() + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String filePath = this.getCurrentSavePath() + File.separator + bucketName + File.separator + key;
        FileUtil.writeBytes(bytes, filePath);
    }

    @Override
    public void storageFile(String bucketName, String key, InputStream inputStream) {
        // 存储路径
        String bucketPath = this.getCurrentSavePath() + File.separator + bucketName;
        if (!FileUtil.exist(bucketPath)) {
            FileUtil.mkdir(bucketPath);
        }

        // 存储文件
        String filePath = this.getCurrentSavePath() + File.separator + bucketName + File.separator + key;
        FileUtil.writeFromStream(inputStream, filePath);
    }

    @Override
    public void deleteFile(String bucketName, String key) {
        // 判断文件是否存在
        String filePath = this.getCurrentSavePath() + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(filePath)) {
            return;
        }

        // 删除文件
        FileUtil.del(filePath);
    }

    @Override
    public byte[] getFileBytes(String bucketName, String key) {
        // 判断文件是否存在
        String filePath = this.getCurrentSavePath() + File.separator + bucketName + File.separator + key;
        if (!FileUtil.exist(filePath)) {
            String errorMessage = StrUtil.format("bucket={},key={}", bucketName, key);
            throw new FileException(FileExceptionEnum.FILE_NOT_FOUND, errorMessage);
        } else {
            return FileUtil.readBytes(filePath);
        }
    }

    @Override
    public String getFileUrl(String bucketName, String key) {
        String contextPath = HttpServletUtil.getRequest().getContextPath();
        return FileConfigExpander.getServerDeployHost() + contextPath + FileConstants.FILE_PREVIEW_BY_OBJECT_NAME + "?fileBucket=" + bucketName + "&fileObjectName=" + key;
    }

    @Override
    public FileLocationEnum getFileLocationEnum() {
        return FileLocationEnum.LOCAL;
    }

    /**
     * 获取当前保存路径
     */
    private String getCurrentSavePath() {
        if (SystemUtil.getOsInfo().isWindows()) {
            return FileConfigExpander.getLocalFileSavePathWindows();
        } else {
            return FileConfigExpander.getLocalFileSavePathLinux();
        }
    }

}
