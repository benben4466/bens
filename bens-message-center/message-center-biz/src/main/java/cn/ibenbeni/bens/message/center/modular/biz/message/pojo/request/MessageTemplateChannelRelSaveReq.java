package cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - 消息模板内容与渠道配置关系创建/修改入参")
public class MessageTemplateChannelRelSaveReq {

    @Schema(description = "主键ID")
    private Long id;

    @NotNull(message = "消息模板内容ID不能为空")
    @Schema(description = "消息模板内容ID")
    private Long templateContentId;

    @NotNull(message = "渠道配置ID不能为空")
    @Schema(description = "渠道配置ID")
    private Long channelConfigId;

}
