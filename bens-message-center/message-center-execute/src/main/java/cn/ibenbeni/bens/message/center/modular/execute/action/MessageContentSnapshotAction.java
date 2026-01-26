package cn.ibenbeni.bens.message.center.modular.execute.action;

import cn.ibenbeni.bens.message.center.api.MessageSendContentSnapshotApi;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendContentSnapshotDTO;
import cn.ibenbeni.bens.message.center.api.constants.chain.MessageCenterChainOrderConstants;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息内容快照 Action
 * 职责: 将解析后的消息内容保存到快照表
 */
@Slf4j
@Component
public class MessageContentSnapshotAction implements MessageHandleAction {

    @Resource
    private MessageSendContentSnapshotApi messageSendContentSnapshotApi;

    @Override
    public void execute(MessageHandleContext context) {
        // 如果前面步骤失败（如解析失败），则不保存快照，或者保存错误信息？
        // 建议仅在解析成功后保存
        if (!context.isSuccess() && context.getFailType() != null) {
            log.warn("[MessageContentSnapshotAction][前置步骤失败，跳过快照保存][业务ID: {}]", context.getBizId());
            return;
        }

        try {
            saveMessageSendContentSnapshot(context);

            log.info("[MessageContentSnapshotAction][快照保存成功][业务ID: {}]", context.getBizId());
        } catch (Exception ex) {
            // 快照保存失败不应阻断发送流程，仅记录日志
            log.error("[MessageContentSnapshotAction][快照保存失败][业务ID: {}]", context.getBizId(), ex);
        }
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.ExecuteLayer.CONTENT_SNAPSHOT;
    }

    private void saveMessageSendContentSnapshot(MessageHandleContext context) {
        MessageSendContentSnapshotDTO contentSnapshot = MessageSendContentSnapshotDTO.builder()
                .sendDetailId(context.getMsgSendDetailId())
                .sendTitle(context.getParsedContent().getTitle())
                .sendMainBody(context.getParsedContent().getMainBodyContent())
                .build();
        messageSendContentSnapshotApi.save(contentSnapshot);
    }

}
