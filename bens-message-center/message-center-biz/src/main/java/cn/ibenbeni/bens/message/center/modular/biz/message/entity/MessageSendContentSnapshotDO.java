package cn.ibenbeni.bens.message.center.modular.biz.message.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.message.center.api.domian.message.SendMessageExtraInfo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息发送内容快照表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_send_content_snapshot", autoResultMap = true)
public class MessageSendContentSnapshotDO extends BaseBusinessEntity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 消息发送执行明细ID
     */
    @TableField("send_detail_id")
    private Long sendDetailId;

    /**
     * 发送标题
     */
    @TableField("send_title")
    private String sendTitle;

    /**
     * 发送内容正文
     */
    @TableField("send_main_body")
    private String sendMainBody;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private Long tenantId;

    /**
     * 发送扩展信息
     */
    @TableField(value = "send_extra_info", typeHandler = JacksonTypeHandler.class)
    private SendMessageExtraInfo sendExtraInfo;

}
