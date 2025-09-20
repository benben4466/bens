package cn.ibenbeni.bens.message.center.modular.notice.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.notice.entity.NoticeDO;
import cn.ibenbeni.bens.message.center.modular.notice.pojo.request.NoticePageReq;
import cn.ibenbeni.bens.message.center.modular.notice.pojo.request.NoticeSaveReq;
import cn.ibenbeni.bens.message.center.modular.notice.pojo.response.NoticeResp;
import cn.ibenbeni.bens.message.center.modular.notice.service.NoticeService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - 通知公告")
@RestController
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @Operation(summary = "创建通知公告")
    @PostResource(path = "/system/notice/create")
    public ResponseData<Long> createNotice(@RequestBody @Valid NoticeSaveReq req) {
        return new SuccessResponseData<>(noticeService.createNotice(req));
    }

    @Operation(summary = "删除通知公告")
    @Parameter(name = "id", description = "通知公告ID", required = true, example = "10")
    @DeleteResource(path = "/system/notice/delete")
    public ResponseData<Boolean> deleteNotice(@RequestParam("id") Long id) {
        noticeService.deleteNotice(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除通知公告")
    @Parameter(name = "ids", description = "通知公告ID集合", required = true)
    @DeleteResource(path = "/system/notice/delete-list")
    public ResponseData<Boolean> deleteNotice(@RequestParam("ids") Set<Long> ids) {
        noticeService.deleteNotice(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "修改通知公告")
    @PostResource(path = "/system/notice/update")
    public ResponseData<Boolean> updateNotice(@RequestBody @Valid NoticeSaveReq req) {
        noticeService.updateNotice(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取通知公告")
    @Parameter(name = "id", description = "通知公告ID", required = true, example = "10")
    @GetResource(path = "/system/notice/get")
    public ResponseData<NoticeResp> getNotice(@RequestParam("id") Long id) {
        NoticeDO notice = noticeService.getNotice(id);
        return new SuccessResponseData<>(BeanUtil.toBean(notice, NoticeResp.class));
    }

    @Operation(summary = "获取通知公告分页列表")
    @GetResource(path = "/system/notice/page")
    public ResponseData<PageResult<NoticeResp>> getNoticePage(@Valid NoticePageReq pageReq) {
        PageResult<NoticeDO> noticePage = noticeService.getNoticePage(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(noticePage, NoticeResp.class));
    }

}
