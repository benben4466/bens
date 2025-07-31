package cn.ibenbeni.bens.file.modular.pojo.request;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - 上传文件入参")
public class FileUploadReq {

    @NotNull(message = "文件附件不能为空")
    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile file;

    @Schema(description = "文件目录", example = "/xxx/yyy/zz")
    private String directory;

    @AssertTrue(message = "文件目录不正确")
    @JsonIgnore
    public boolean isDirectoryValid() {
        return !StrUtil.containsAny(directory, "..", "/", "\\");
    }

}
