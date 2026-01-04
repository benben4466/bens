package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 消息模板内容分页入参")
public class MessageTemplateContentPageReq extends PageParam {

    @Schema(description = "模板ID")
    private Long templateId;

    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "标题,模糊匹配")
    private String title;
}

