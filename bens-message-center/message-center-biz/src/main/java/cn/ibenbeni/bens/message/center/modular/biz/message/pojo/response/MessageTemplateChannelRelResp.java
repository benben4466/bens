package cn.ibenbeni.bens.message.center.modular.biz.message.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 消息模板内容与渠道配置关系响应")
public class MessageTemplateChannelRelResp {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "消息模板内容ID")
    private Long templateContentId;

    @Schema(description = "渠道配置ID")
    private Long channelConfigId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
