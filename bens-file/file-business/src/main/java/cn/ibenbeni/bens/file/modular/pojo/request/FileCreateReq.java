package cn.ibenbeni.bens.file.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - 文件创建入参")
public class FileCreateReq {

    @NotNull(message = "文件配置编码不能为空")
    @Schema(description = "文件配置编码", example = "aliyunoss", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigCode;

    @NotNull(message = "文件路径不能为空")
    @Schema(description = "文件路径", example = "xxx.jpg")
    private String filePath;

    @NotNull(message = "原文件名称不能为空")
    @Schema(description = "原文件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "ibenbeni.jpg")
    private String fileOriginName;

    @NotNull(message = "文件访问URL不能为空")
    @Schema(description = "文件访问URL", example = "http://www.ibenbeni.cn/xxx.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileUrl;

    @Schema(description = "文件MIME类型", example = "application/octet-stream")
    private String fileType;

    @Schema(description = "文件大小(KB)", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer fileSizeKb;

}
