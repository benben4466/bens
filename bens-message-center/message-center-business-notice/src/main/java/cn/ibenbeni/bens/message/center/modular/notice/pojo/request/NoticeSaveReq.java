package cn.ibenbeni.bens.message.center.modular.notice.pojo.request;

import cn.ibenbeni.bens.message.center.api.enums.notice.NoticeTypeEnum;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(description = "管理后台 - 通知公告创建/修改入参")
public class NoticeSaveReq {

    /**
     * 通知公告ID
     */
    @Schema(description = "通知公告ID", example = "10")
    private Long noticeId;

    /**
     * 通知公告标题
     */
    @NotBlank(message = "通知公告标题不能为空")
    @Size(max = 50, message = "通知公告标题不能超过50个字符")
    @Schema(description = "通知公告标题", example = "笨笨科技")
    private String noticeTitle;

    /**
     * 通知公告内容
     */
    @Schema(description = "通知公告内容", example = "笨笨科技成立")
    private String noticeContent;

    /**
     * 通知公告类型
     * <p>枚举值：{@link NoticeTypeEnum}</p>
     */
    @NotNull(message = "通知公告类型不能为空")
    @Schema(description = "通知公告类型", example = "1")
    private Integer noticeType;

    /**
     * 通知公告状态
     * <p>枚举值：{@link StatusEnum}</p>
     */
    @Schema(description = "通知公告状态", example = "1")
    private Integer statusFlag;

}
