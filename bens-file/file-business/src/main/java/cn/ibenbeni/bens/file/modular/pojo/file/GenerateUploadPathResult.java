package cn.ibenbeni.bens.file.modular.pojo.file;

import lombok.Data;

/**
 * 生成文件上传路径
 */
@Data
public class GenerateUploadPathResult {

    /**
     * 文件上传路径
     */
    private String fileUploadPath;

    /**
     * 文件存储名称
     */
    private String fileObjectName;

}
