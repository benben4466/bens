package cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.response;

import cn.ibenbeni.bens.message.center.api.enums.notice.NoticeTypeEnum;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 通知公告信息响应")
public class NoticeResp {

    @Schema(description = "通知公告ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long noticeId;

    @Schema(description = "通知公告标题", example = "笨笨科技", requiredMode = Schema.RequiredMode.REQUIRED)
    private String noticeTitle;

    @Schema(description = "通知公告内容", example = "笨笨科技成立", requiredMode = Schema.RequiredMode.REQUIRED)
    private String noticeContent;

    /**
     * <p>枚举值：{@link NoticeTypeEnum}</p>
     */
    @Schema(description = "通知公告类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer noticeType;

    /**
     * <p>枚举值：{@link StatusEnum}</p>
     */
    @Schema(description = "通知公告状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "时间戳格式")
    private LocalDateTime createTime;

}
