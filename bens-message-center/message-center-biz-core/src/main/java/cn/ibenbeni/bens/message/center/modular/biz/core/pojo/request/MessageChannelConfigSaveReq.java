package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 消息渠道配置创建/修改入参")
public class MessageChannelConfigSaveReq {

    @Schema(description = "配置ID")
    private Long configId;

    @NotBlank(message = "渠道编码不能为空")
    @Schema(description = "渠道编码")
    private String channelCode;

    @NotBlank(message = "渠道名称不能为空")
    @Schema(description = "渠道名称")
    private String channelName;

    @NotNull(message = "渠道类型不能为空")
    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "账户配置")
    private Map<String, Object> accountConfig;

    @Schema(description = "状态")
    private StatusEnum statusFlag;

    @Schema(description = "备注")
    private String remark;

}
