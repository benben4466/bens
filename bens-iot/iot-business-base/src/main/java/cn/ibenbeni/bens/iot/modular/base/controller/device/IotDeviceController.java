package cn.ibenbeni.bens.iot.modular.base.controller.device;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDevicePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.device.IotDeviceResp;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
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

@Tag(name = "管理后台 - IOT设备")
@RestController
public class IotDeviceController {

    @Resource
    private IotDeviceService deviceService;

    @Operation(summary = "创建设备")
    @PostResource(path = "/iot/device/create")
    public ResponseData<Long> createDevice(@RequestBody @Valid IotDeviceSaveReq saveReq) {
        return new SuccessResponseData<>(deviceService.createDevice(saveReq));
    }

    @Operation(summary = "删除设备")
    @Parameter(name = "id", description = "设备ID", required = true)
    @DeleteResource(path = "/iot/device/delete")
    public ResponseData<Boolean> deleteDevice(@RequestParam("id") Long id) {
        deviceService.deleteDevice(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除设备")
    @Parameter(name = "ids", description = "设备ID集合", required = true)
    @DeleteResource(path = "/iot/device/delete-list")
    public ResponseData<Boolean> deleteDevice(@RequestParam("ids") Set<Long> ids) {
        deviceService.deleteDevice(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新设备")
    @PutResource(path = "/iot/device/update")
    public ResponseData<Boolean> updateDevice(@RequestBody @Valid IotDeviceSaveReq updateReq) {
        deviceService.updateDevice(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取设备")
    @GetResource(path = "/iot/device/get")
    public ResponseData<IotDeviceResp> getDevice(@RequestParam("id") Long id) {
        IotDeviceDO device = deviceService.getDevice(id);
        return new SuccessResponseData<>(BeanUtil.toBean(device, IotDeviceResp.class));
    }

    @Operation(summary = "获取设备分页列表")
    @GetResource(path = "/iot/device/page")
    public ResponseData<PageResult<IotDeviceResp>> pageDevice(IotDevicePageReq pageReq) {
        PageResult<IotDeviceDO> page = deviceService.pageDevice(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotDeviceResp.class));
    }

}
