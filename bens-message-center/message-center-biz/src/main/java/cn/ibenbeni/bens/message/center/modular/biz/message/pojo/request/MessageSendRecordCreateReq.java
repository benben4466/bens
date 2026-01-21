package cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgRecipientTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 消息发送记录创建入参")
public class MessageSendRecordCreateReq {

    @NotNull(message = "关联模板ID不能为空")
    @Schema(description = "关联模板ID")
    private Long templateId;

    @NotBlank(message = "模板编码不能为空")
    @Schema(description = "模板编码")
    private String templateCode;

    @NotEmpty(message = "业务类型不能为空")
    @Schema(description = "业务类型")
    private String bizType;

    @NotEmpty(message = "业务关联ID不能为空")
    @Schema(description = "业务关联ID")
    private String bizId;

    @Schema(description = "发送时的标题")
    private String msgTitle;

    @Schema(description = "模板参数变量")
    private Map<String, Object> msgVariables;

    @NotNull(message = "渠道类型不能为空")
    @Schema(description = "渠道类型")
    private Integer channelType;

    @NotNull(message = "接收人类型不能为空")
    @Schema(description = "接收人类型")
    private MsgRecipientTypeEnum recipientType;

    @Schema(description = "接收者信息")
    private Map<String, Object> recipient;

}
