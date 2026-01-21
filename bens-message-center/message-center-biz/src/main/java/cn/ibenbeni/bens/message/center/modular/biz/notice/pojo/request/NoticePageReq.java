package cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.message.center.api.enums.notice.NoticeTypeEnum;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 通知公告分页入参")
public class NoticePageReq extends PageParam {

    /**
     * 通知公告标题
     */
    @Schema(description = "通知公告标题,模糊匹配", example = "笨笨科技")
    private String noticeTitle;

    /**
     * 通知公告类型
     * <p>枚举值：{@link NoticeTypeEnum}</p>
     */
    @Schema(description = "通知公告类型", example = "1")
    private Integer noticeType;

    /**
     * 通知公告状态
     * <p>枚举值：{@link StatusEnum}</p>
     */
    @Schema(description = "通知公告状态", example = "1")
    private Integer statusFlag;

}
