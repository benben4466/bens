package cn.ibenbeni.bens.file.modular.pojo.response;

import cn.ibenbeni.bens.file.api.client.FileClientConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 文件配置响应")
public class FileConfigResp {

    @Schema(description = "文件配置ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fileConfigId;

    @NotEmpty(message = "文件配置名称不能为空")
    @Schema(description = "文件配置名称", example = "本地存储", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigName;

    @NotEmpty(message = "文件配置编码不能为空")
    @Schema(description = "文件配置编码", example = "LOCAL_STORAGE", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigCode;

    /**
     * <p>枚举值: {@link cn.ibenbeni.bens.file.api.enums.FileStorageEnum}</p>
     */
    @NotNull(message = "文件存储器不能为空")
    @Schema(description = "文件存储器", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer fileStorage;

    @Schema(description = "是否主配置", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean masterFlag;

    @Schema(description = "文件存储配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private FileClientConfig storageConfig;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
