package cn.ibenbeni.bens.message.center.modular.execute.service.impl;

import cn.ibenbeni.bens.common.chain.core.ChainProcessor;
import cn.ibenbeni.bens.message.center.api.MessageSendDetailApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageQueuePayload;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendDetailDTO;
import cn.ibenbeni.bens.message.center.modular.execute.action.MessageHandleAction;
import cn.ibenbeni.bens.message.center.modular.execute.idempotent.MessageIdempotentChecker;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.service.MessageHandlerService;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    @Resource
    private List<MessageHandleAction> actions;

    @Resource
    private MessageSendDetailApi messageSendDetailApi;

    @Autowired(required = false)
    private MessageIdempotentChecker idempotentChecker;

    /**
     * 责任链处理器
     */
    private ChainProcessor<MessageHandleContext> chainProcessor;

    @PostConstruct
    public void init() {
        // 初始化责任链处理器
        chainProcessor = new ChainProcessor<>(actions, "MessageHandle");
    }

    @Override
    public boolean handleMessage(MessageQueuePayload payload) {
        log.info("[MessageHandlerServiceImpl][开始处理消息][业务ID: {}, channelType: {}]", payload.getBizId(), payload.getChannelType());

        if (payload.getTenantId() == null) {
            log.error("[handleMessage][忽略处理][租户ID为空][载荷: {}]", JSON.toJSONString(payload));
            return true;
        }
        TenantContextHolder.setTenantId(payload.getTenantId()); // 设置租户ID

        // 幂等性检查
        if (idempotentChecker != null) {
            // TODO [优化] MessageQueuePayload 增加任务ID
            if (!idempotentChecker.tryAcquire(null, payload.getChannelType())) {
                log.info("[MessageHandlerServiceImpl][幂等拦截，跳过处理][业务ID: {}]", payload.getBizId());
                return true; // 返回true，避免MQ重试
            }
        }

        try {
            MessageHandleContext context = buildContext(payload);

            // 执行责任链
            chainProcessor.execute(context);

            if (context.isSuccess()) {
                // TODO [问题] 填充消息发送明细
                // 更新 Detail 状态
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                detail.setSendStatus(MessageDetailStatusEnum.SUCCESS);
                detail.setOutSerialNumber(String.valueOf(context.getResponseData())); // 假设 ResponseData 是 SerialNum
                detail.setFinishTime(new java.util.Date());
                messageSendDetailApi.updateDetail(detail);

                log.info("[MessageHandlerServiceImpl][消息处理成功][业务ID: {}]", context.getBizId());
                return true;
            } else {
                // TODO [问题] 填充消息发送明细
                // 更新 Detail 失败
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                detail.setSendStatus(MessageDetailStatusEnum.FAIL);
                detail.setOutResp(context.getFailReason());
                detail.setFinishTime(new java.util.Date());
                messageSendDetailApi.updateDetail(detail);

                log.error("[MessageHandlerServiceImpl][消息处理失败][业务ID: {}, failReason: {}]", context.getBizId(), context.getFailReason());
                return false;
            }

        } catch (Exception ex) {
            log.error("[MessageHandlerServiceImpl][消息处理异常][业务ID: {}]", payload.getBizId(), ex);
            try {
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                // TODO [优化] 去掉记录ID
                detail.setId(null);
                detail.setSendStatus(MessageDetailStatusEnum.FAIL);
                detail.setOutResp(ex.getMessage());
                messageSendDetailApi.updateDetail(detail);
            } catch (Exception updateEx) {
                log.error("[MessageHandlerServiceImpl][更新记录状态失败]", updateEx);
            }
            return false;
        }
    }

    private MessageHandleContext buildContext(MessageQueuePayload payload) {
        MessageHandleContext context = new MessageHandleContext();
        context.setBizId(payload.getBizId());
        context.setChannelType(payload.getChannelType());
        context.setTemplateCode(payload.getTemplateCode());
        context.setMsgVariables(payload.getMsgVariables());
        context.setRecipientInfos(payload.getRecipientInfos());
        context.setTenantId(payload.getTenantId());
        return context;
    }

}