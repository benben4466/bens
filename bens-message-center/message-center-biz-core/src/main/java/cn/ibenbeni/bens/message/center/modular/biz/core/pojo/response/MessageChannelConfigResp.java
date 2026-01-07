package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.response;

import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 消息渠道配置响应")
public class MessageChannelConfigResp {

    @Schema(description = "配置ID")
    private Long configId;

    @Schema(description = "渠道编码")
    private String channelCode;

    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "账户配置")
    private Map<String, Object> accountConfig;

    @Schema(description = "状态")
    private StatusEnum statusFlag;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
