package cn.ibenbeni.bens.file.api.pojo.prop;

import lombok.Data;

/**
 * 本地文件存储配置
 *
 * @author: benben
 * @time: 2025/6/22 下午4:32
 */
@Data
public class LocalFileProp {

    /**
     * 本地文件存储位置（linux）
     */
    private String localFileSavePathLinux = "/opt/bensFilePath";

    /**
     * 本地文件存储位置（windows）
     */
    private String localFileSavePathWin = "D:\\bensFilePath";

}
