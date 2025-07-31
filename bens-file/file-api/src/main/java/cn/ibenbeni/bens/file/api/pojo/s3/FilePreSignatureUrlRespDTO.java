package cn.ibenbeni.bens.file.api.pojo.s3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件预签名地址响应DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePreSignatureUrlRespDTO {

    /**
     * 文件上传 URL（用于上传）
     */
    private String fileUploadUrl;

    /**
     * 文件URL（用于读取、下载等）
     */
    private String fileUrl;

}
