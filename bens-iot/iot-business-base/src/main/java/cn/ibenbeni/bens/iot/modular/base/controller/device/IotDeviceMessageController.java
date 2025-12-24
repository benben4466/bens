package cn.ibenbeni.bens.iot.modular.base.controller.device;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceMessageDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceMessagePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceMessageSendReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.device.IotDeviceMessageResp;
import cn.ibenbeni.bens.iot.modular.base.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "管理后台 - IOT设备消息")
@RestController
public class IotDeviceMessageController {

    @Resource
    private IotDeviceMessageService deviceMessageService;

    @Operation(summary = "获得设备消息分页")
    @GetResource(path = "/iot/device/message/page")
    public ResponseData<PageResult<IotDeviceMessageResp>> pageDeviceMessage(@Valid IotDeviceMessagePageReq pageReq) {
        PageResult<IotDeviceMessageDO> page = deviceMessageService.pageDeviceMessage(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotDeviceMessageResp.class));
    }

    @Operation(summary = "发送消息", description = "可用于设备模拟")
    @PostResource(path = "/iot/device/message/send")
    public ResponseData<Boolean> sendDeviceMessage(@RequestBody @Valid IotDeviceMessageSendReq sendReq) {
        deviceMessageService.sendDeviceMessage(BeanUtil.toBean(sendReq, IotDeviceMessage.class));
        return new SuccessResponseData<>(true);
    }

}
