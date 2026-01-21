package cn.ibenbeni.bens.message.center.modular.biz.message.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageChannelConfigPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageChannelConfigSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.response.MessageChannelConfigResp;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageChannelConfigService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - 消息渠道配置")
@RestController
public class MessageChannelConfigController {

    @Resource
    private MessageChannelConfigService messageChannelConfigService;

    @Operation(summary = "创建消息渠道配置")
    @PostResource(path = "/msg-center/channel-config/create")
    public ResponseData<Long> create(@RequestBody @Valid MessageChannelConfigSaveReq req) {
        return new SuccessResponseData<>(messageChannelConfigService.create(req));
    }

    @Operation(summary = "修改消息渠道配置")
    @PutResource(path = "/msg-center/channel-config/update")
    public ResponseData<Boolean> update(@RequestBody @Valid MessageChannelConfigSaveReq req) {
        messageChannelConfigService.updateById(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除消息渠道配置")
    @Parameter(name = "id", description = "配置ID", required = true, example = "10")
    @DeleteResource(path = "/msg-center/channel-config/delete")
    public ResponseData<Boolean> delete(@RequestParam("id") Long id) {
        messageChannelConfigService.deleteById(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除消息渠道配置")
    @Parameter(name = "ids", description = "配置ID集合", required = true)
    @DeleteResource(path = "/msg-center/channel-config/delete-list")
    public ResponseData<Boolean> deleteList(@RequestParam("ids") Set<Long> ids) {
        messageChannelConfigService.deleteByIds(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取消息渠道配置")
    @Parameter(name = "id", description = "配置ID", required = true, example = "10")
    @GetResource(path = "/msg-center/channel-config/get")
    public ResponseData<MessageChannelConfigResp> get(@RequestParam("id") Long id) {
        MessageChannelConfigDO entity = messageChannelConfigService.getById(id);
        return new SuccessResponseData<>(BeanUtil.toBean(entity, MessageChannelConfigResp.class));
    }

    @Operation(summary = "获取消息渠道配置分页列表")
    @GetResource(path = "/msg-center/channel-config/page")
    public ResponseData<PageResult<MessageChannelConfigResp>> page(@Valid MessageChannelConfigPageReq req) {
        PageResult<MessageChannelConfigDO> page = messageChannelConfigService.page(req);
        return new SuccessResponseData<>(DbUtil.toBean(page, MessageChannelConfigResp.class));
    }

}
