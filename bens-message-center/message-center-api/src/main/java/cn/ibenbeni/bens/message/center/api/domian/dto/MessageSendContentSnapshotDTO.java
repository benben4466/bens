package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.message.center.api.domian.message.SendMessageExtraInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息发送内容快照 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendContentSnapshotDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 消息发送执行明细ID
     */
    private Long sendDetailId;

    /**
     * 发送标题
     */
    private String sendTitle;

    /**
     * 发送内容正文
     */
    private String sendMainBody;

    /**
     * 租户编号
     */
    private Long tenantId;

    /**
     * 发送扩展信息
     */
    private SendMessageExtraInfo sendExtraInfo;

}
