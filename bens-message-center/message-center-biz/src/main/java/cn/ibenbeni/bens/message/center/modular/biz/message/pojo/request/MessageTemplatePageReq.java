package cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 消息模板分页入参")
public class MessageTemplatePageReq extends PageParam {

    @Schema(description = "模板名称,模糊匹配")
    private String templateName;

    @Schema(description = "模板编码,模糊匹配")
    private String templateCode;

    @Schema(description = "模板状态")
    private Integer templateStatus;

    @Schema(description = "审核状态")
    private Integer auditStatus;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "创建时间范围", example = "时间戳数组，长度为2")
    private LocalDateTime[] createTime;
}

