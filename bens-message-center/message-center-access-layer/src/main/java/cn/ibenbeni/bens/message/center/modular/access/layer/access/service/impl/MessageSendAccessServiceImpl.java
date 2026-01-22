package cn.ibenbeni.bens.message.center.modular.access.layer.access.service.impl;

import cn.ibenbeni.bens.common.chain.core.ChainProcessor;
import cn.ibenbeni.bens.message.center.api.util.MessageUniqueIdUtils;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.action.MessageSendAction;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.UserSendMessageContext;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.service.MessageSendAccessService;
import cn.ibenbeni.bens.message.center.api.MessageSendApi;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendRequest;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendResponse;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 消息发送接入层服务实现
 * 同时实现 MessageSendApi 接口，作为统一的对外 API
 */
@Slf4j
@Service
public class MessageSendAccessServiceImpl implements MessageSendAccessService, MessageSendApi {

    @Resource
    private List<MessageSendAction> actions;

    /**
     * 责任链处理器
     */
    private ChainProcessor<UserSendMessageContext> chainProcessor;

    @PostConstruct
    public void init() {
        // 初始化责任链处理器
        chainProcessor = new ChainProcessor<>(actions, "MessageSend");
    }

    @Override
    public MessageSendResponse send(MessageSendRequest request) {
        log.info("[MessageSendAccessServiceImpl][开始发送消息][templateCode: {}]", request.getTemplateCode());

        try {
            // 1. 构建上下文
            UserSendMessageContext context = buildContext(request);

            // 2. 执行责任链
            chainProcessor.execute(context);

            // 3. 检查是否中断
            if (context.isInterrupted()) {
                return MessageSendResponse.fail(context.getErrorMessage());
            }

            // 4. 返回成功结果
            log.info("[MessageSendAccessServiceImpl][消息发送成功][recordIds: {}]", context.getRecordIds());
            return MessageSendResponse.success(context.getBizId());
        } catch (MessageCenterException mEx) {
            log.error("[MessageSendAccessServiceImpl][消息发送失败][业务异常]", mEx);
            return MessageSendResponse.fail(mEx.getUserTip());
        } catch (Exception ex) {
            log.error("[MessageSendAccessServiceImpl][消息发送失败][系统异常]", ex);
            return MessageSendResponse.fail("消息发送失败：" + ex.getMessage());
        }
    }

    /**
     * 构建消息发送上下文
     */
    private UserSendMessageContext buildContext(MessageSendRequest request) {
        UserSendMessageContext context = new UserSendMessageContext();
        context.setTemplateCode(request.getTemplateCode());
        context.setTemplateParams(request.getTemplateParams());
        context.setBizId(MessageUniqueIdUtils.generateBizId()); // bizId 纯数字
        context.setRecipientType(request.getRecipientType());
        context.setRecipient(request.getRecipient());
        context.setChannels(request.getChannels());
        context.setTenantId(TenantContextHolder.getRequiredTenantId());
        return context;
    }

}
