package cn.ibenbeni.bens.config.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 系统参数初始化入参")
public class ConfigInitReq {

    /**
     * 系统参数配置集合
     * <p>Key=参数编码；Value=参数值</p>
     */
    @NotEmpty(message = "系统参数配置集合不能为空")
    @Schema(description = "系统参数配置集合")
    private Map<String, String> sysConfigMap;

}
