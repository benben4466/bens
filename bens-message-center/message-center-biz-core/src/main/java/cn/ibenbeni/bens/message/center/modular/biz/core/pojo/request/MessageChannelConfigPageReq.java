package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 消息渠道配置分页查询入参")
public class MessageChannelConfigPageReq extends PageParam {

    @Schema(description = "渠道编码")
    private String channelCode;

    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "状态")
    private StatusEnum statusFlag;

}
