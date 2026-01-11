package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgRecipientTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 消息发送记录创建入参")
public class MessageSendRecordCreateReq {

    @Schema(description = "关联模板ID")
    private Long templateId;

    @NotBlank(message = "模板编码不能为空")
    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "业务关联ID")
    private String bizId;

    @Schema(description = "发送时的标题")
    private String msgTitle;

    @Schema(description = "模板参数变量")
    private Map<String, Object> msgVariables;

    @NotNull(message = "渠道类型不能为空")
    @Schema(description = "渠道类型")
    private Integer channelType;

    @Schema(description = "接收者信息")
    private Map<String, Object> recipient;

    @NotNull(message = "接收人类型不能为空")
    @Schema(description = "接收人类型")
    private MsgRecipientTypeEnum recipientType;

    @NotNull(message = "发送状态不能为空")
    @Schema(description = "发送状态")
    private MsgSendStatusEnum sendStatus;

    @Schema(description = "重试次数")
    private Integer retryCount;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "响应数据")
    private String responseData;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

}
