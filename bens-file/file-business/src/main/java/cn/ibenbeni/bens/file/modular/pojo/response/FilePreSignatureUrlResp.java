package cn.ibenbeni.bens.file.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "管理后台 - 文件预签名地址响应")
public class FilePreSignatureUrlResp {

    @Schema(description = "文件配置ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fileConfigId;

    @Schema(description = "文件配置编码", example = "aliyunoss", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigCode;

    @Schema(description = "文件上传URL",
            example = "https://s3.cn-south-1.qiniucs.com/ruoyi-vue-pro/758d3a5387507358c7236de4c8f96de1c7f5097ff6a7722b34772fb7b76b140f.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS%2F20240217%2Fcn-south-1%2Fs3%2Faws4_request&X-Amz-Date=20240217T123222Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=a29f33770ab79bf523ccd4034d0752ac545f3c2a3b17baa1eb4e280cfdccfda5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fileUploadUrl;

    @Schema(description = "文件访问URL", example = "http://testone.ibenbeni.cn/123.png", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileUrl;

    /**
     * 若是S3直传，则需要前端传送完毕后，调用后端接口createFile接口创建文件
     */
    @Schema(description = "文件路径", example = "xxx.png", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;

}
