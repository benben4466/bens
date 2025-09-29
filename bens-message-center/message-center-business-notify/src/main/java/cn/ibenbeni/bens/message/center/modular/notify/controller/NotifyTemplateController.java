package cn.ibenbeni.bens.message.center.modular.notify.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyTemplateSaveReq;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyTemplateSendReq;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.response.NotifyTemplateResp;
import cn.ibenbeni.bens.message.center.modular.notify.service.NotifySendService;
import cn.ibenbeni.bens.message.center.modular.notify.service.NotifyTemplateService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.enums.user.UserTypeEnum;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - 站内信模板")
@RestController
public class NotifyTemplateController {

    @Resource
    private NotifyTemplateService notifyTemplateService;

    @Resource
    private NotifySendService notifySendService;

    @Operation(summary = "创建站内信模版")
    @PostResource(path = "/system/notify-template/create")
    public ResponseData<Long> createNotifyTemplate(@RequestBody @Valid NotifyTemplateSaveReq req) {
        return new SuccessResponseData<>(notifyTemplateService.createNotifyTemplate(req));
    }

    @Operation(summary = "删除站内信模版")
    @Parameter(name = "id", description = "模板ID", required = true)
    @DeleteResource(path = "/system/notify-template/delete")
    public ResponseData<Boolean> deleteNotifyTemplate(@RequestParam("id") Long id) {
        notifyTemplateService.deleteNotifyTemplate(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除站内信模版")
    @Parameter(name = "ids", description = "模板ID集合", required = true)
    @DeleteResource(path = "/system/notify-template/delete-list")
    public ResponseData<Boolean> deleteNotifyTemplate(@RequestParam("ids") Set<Long> id) {
        notifyTemplateService.deleteNotifyTemplate(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新站内信模版")
    @PostResource(path = "/system/notify-template/update")
    public ResponseData<Boolean> updateNotifyTemplate(@RequestBody @Valid NotifyTemplateSaveReq req) {
        notifyTemplateService.updateNotifyTemplate(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得站内信模版")
    @Parameter(name = "id", description = "模板ID", required = true, example = "10")
    @GetResource(path = "/system/notify-template/get")
    public ResponseData<NotifyTemplateResp> getNotifyTemplate(@RequestParam("id") Long id) {
        NotifyTemplateDO notifyTemplate = notifyTemplateService.getNotifyTemplate(id);
        return new SuccessResponseData<>(BeanUtil.toBean(notifyTemplate, NotifyTemplateResp.class));
    }

    @Operation(summary = "获得站内信模版分页")
    @GetResource(path = "/system/notify-template/page")
    public ResponseData<PageResult<NotifyTemplateResp>> pageNotifyTemplate(NotifyTemplatePageReq req) {
        PageResult<NotifyTemplateDO> page = notifyTemplateService.pageNotifyTemplate(req);
        return new SuccessResponseData<>(DbUtil.toBean(page, NotifyTemplateResp.class));
    }

    @Operation(summary = "发送站内信")
    @PostResource(path = "/system/notify-template/send-notify")
    public ResponseData<Long> sendNotify(@RequestBody @Valid NotifyTemplateSendReq sendReq) {
        if (UserTypeEnum.ADMIN.getType().equals(sendReq.getUserType())) {
            Long messageId = notifySendService.sendSingleNotifyToAdmin(sendReq.getUserId(), sendReq.getTemplateCode(), sendReq.getTemplateParams());
            return new SuccessResponseData<>(messageId);
        }
        return new SuccessResponseData<>();
    }

}
