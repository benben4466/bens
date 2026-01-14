package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 消息模板内容与渠道配置关系分页查询入参")
public class MessageTemplateChannelRelPageReq extends PageParam {

    @Schema(description = "消息模板内容ID")
    private Long templateContentId;

    @Schema(description = "渠道配置ID")
    private Long channelConfigId;

}
