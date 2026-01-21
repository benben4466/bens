package cn.ibenbeni.bens.message.center.modular.biz.notify.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyMessageDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyMessageMyPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyMessagePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.response.NotifyMessageResp;
import cn.ibenbeni.bens.message.center.modular.biz.notify.service.NotifyMessageService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.enums.user.UserTypeEnum;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Tag(name = "管理后台 - 站内信消息")
@RestController
public class NotifyMessageController {

    @Resource
    private NotifyMessageService notifyMessageService;

    // ========== 管理所有的站内信 ==========

    @Operation(summary = "获得站内信")
    @Parameter(name = "id", description = "消息ID", required = true, example = "10")
    @GetResource(path = "/system/notify-message/get")
    public ResponseData<NotifyMessageResp> getNotifyMessage(@RequestParam("id") Long id) {
        NotifyMessageDO notifyMessage = notifyMessageService.getNotifyMessage(id);
        return new SuccessResponseData<>(BeanUtil.toBean(notifyMessage, NotifyMessageResp.class));
    }

    @Operation(summary = "获得站内信分页")
    @GetResource(path = "/system/notify-message/page")
    public ResponseData<PageResult<NotifyMessageResp>> pageNotifyMessage(@Valid NotifyMessagePageReq pageReq) {
        PageResult<NotifyMessageDO> page = notifyMessageService.pageNotifyMessage(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, NotifyMessageResp.class));
    }

    // ========== 查看自己的站内信 ==========

    @Operation(summary = "获得我的站内信分页")
    @GetResource(path = "/system/notify-message/my-page")
    public ResponseData<PageResult<NotifyMessageResp>> pageMyNotifyMessage(@Valid NotifyMessageMyPageReq pageReq) {
        PageResult<NotifyMessageDO> page = notifyMessageService.pageMyNotifyMessage(pageReq, LoginUserHolder.get().getUserId(), UserTypeEnum.ADMIN.getType());
        return new SuccessResponseData<>(DbUtil.toBean(page, NotifyMessageResp.class));
    }

    @Operation(summary = "标记站内信为已读")
    @Parameter(name = "ids", description = "站内信ID集合", required = true)
    @PutResource(path = "/system/notify-message/update-read")
    public ResponseData<Boolean> updateNotifyMessageRead(@RequestParam("ids") Set<Long> ids) {
        notifyMessageService.updateNotifyMessageRead(ids, LoginUserHolder.get().getUserId(), UserTypeEnum.ADMIN.getType());
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "标记所有站内信为已读")
    @PutResource(path = "/system/notify-message/update-all-read")
    public ResponseData<Boolean> updateAllNotifyMessageRead() {
        notifyMessageService.updateAllNotifyMessageRead(LoginUserHolder.get().getUserId(), UserTypeEnum.ADMIN.getType());
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取当前用户的最新站内信列表，默认10条")
    @Parameter(name = "size", description = "10")
    @GetResource(path = "/system/notify-message/get-unread-list")
    public ResponseData<List<NotifyMessageResp>> listUnreadNotifyMessage(@RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<NotifyMessageDO> notifyMessages = notifyMessageService.listUnreadNotifyMessage(LoginUserHolder.get().getUserId(), UserTypeEnum.ADMIN.getType(), size);
        return new SuccessResponseData<>(BeanUtils.toBean(notifyMessages, NotifyMessageResp.class));
    }

    @Operation(summary = "获得当前用户的未读站内信数量")
    @GetResource(path = "/system/notify-message/get-unread-count")
    public ResponseData<Long> getUnreadNotifyMessageCount() {
        return new SuccessResponseData<>(notifyMessageService.getUnreadNotifyMessageCount(LoginUserHolder.get().getUserId(), UserTypeEnum.ADMIN.getType()));
    }

}
