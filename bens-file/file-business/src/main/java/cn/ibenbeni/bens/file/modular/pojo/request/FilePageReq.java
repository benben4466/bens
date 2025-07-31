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
@Schema(description = "管理后台 - 文件分页入参")
public class FilePageReq extends PageParam {

    @Schema(description = "文件存储路径,模糊匹配", example = "/20250731/xxx.jpg")
    private String filePath;

    @Schema(description = "文件类型,模糊匹配", example = "jpg")
    private String fileType;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
