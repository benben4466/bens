package cn.ibenbeni.bens.file.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 文件响应(不返回文件内容)")
public class FileResp {

    @Schema(description = "文件ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fileId;

    @Schema(description = "文件配置编码", example = "aliyunoss", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigCode;

    @Schema(description = "文件原始名称", example = "xxxx.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileOriginName;

    @Schema(description = "文件存储名称", example = "xxxx222.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileObjectName;

    @Schema(description = "文件路径", example = "/20250731/xxx.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;

    @Schema(description = "文件URL", example = "http://ibenbeni.cn/20250731/xxx.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileUrl;

    @Schema(description = "文件大小(单位:KB)", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fileSizeKb;

    @Schema(description = "文件MIME类型", example = "application/octet-stream", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileType;

    @Schema(description = "文件后缀", example = "txt", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileSuffix;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
