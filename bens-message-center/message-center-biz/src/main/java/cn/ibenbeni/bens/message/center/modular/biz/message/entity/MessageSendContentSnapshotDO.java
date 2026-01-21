package cn.ibenbeni.bens.message.center.modular.biz.message.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息发送内容快照表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("message_send_content_snapshot")
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

}
