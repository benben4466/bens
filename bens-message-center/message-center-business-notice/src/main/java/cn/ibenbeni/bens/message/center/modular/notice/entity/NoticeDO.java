package cn.ibenbeni.bens.message.center.modular.notice.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.message.center.api.enums.notice.NoticeTypeEnum;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notice", autoResultMap = true)
public class NoticeDO extends BaseBusinessEntity {

    /**
     * 通知公告ID
     */
    @TableId(value = "notice_id", type = IdType.ASSIGN_ID)
    private Long noticeId;

    /**
     * 通知公告标题
     */
    @TableField("notice_title")
    private String noticeTitle;

    /**
     * 通知公告内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 通知公告类型
     * <p>枚举值：{@link NoticeTypeEnum}</p>
     */
    @TableField("notice_type")
    private Integer noticeType;

    /**
     * 通知公告状态
     * <p>枚举值：{@link StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
