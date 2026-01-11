package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 消息发送记录分页查询入参")
public class MessageSendRecordPageReq extends PageParam {

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "业务关联ID")
    private String bizId;

    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "发送状态")
    private MsgSendStatusEnum sendStatus;

    @Schema(description = "失败原因类型")
    private MsgSendFailTypeEnum failType;

    @Schema(description = "发送时间范围")
    private List<LocalDateTime> sendTime;
}
