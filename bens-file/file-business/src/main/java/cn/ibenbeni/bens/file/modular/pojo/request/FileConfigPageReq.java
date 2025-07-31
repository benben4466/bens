package cn.ibenbeni.bens.file.modular.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 文件配置分页入参")
public class FileConfigPageReq extends PageParam {

    @Schema(description = "文件配置名称", example = "本地存储", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigName;

    @Schema(description = "文件配置编码", example = "LOCAL_STORAGE", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileConfigCode;

    /**
     * <p>枚举值: {@link cn.ibenbeni.bens.file.api.enums.FileStorageEnum}</p>
     */
    @Schema(description = "文件存储器", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer fileStorage;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
