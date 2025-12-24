package cn.ibenbeni.bens.iot.modular.base.controller.device;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceGroupDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceGroupSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.device.IotDeviceGroupResp;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceGroupService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台 - IOT设备分组关系")
@RestController
public class IotDeviceGroupController {

    @Resource
    private IotDeviceGroupService deviceGroupService;

    @Operation(summary = "创建设备分组关系")
    @PostResource(path = "/iot/device/group/create")
    public ResponseData<Boolean> createDeviceGroup(@RequestBody @Valid IotDeviceGroupSaveReq saveReq) {
        deviceGroupService.createDeviceGroup(saveReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "根据设备分组ID,删除设备分组关系")
    @Parameter(name = "groupId", description = "设备分组ID", required = true)
    @DeleteResource(path = "/iot/device/group/delete-by-group-id")
    public ResponseData<Boolean> deleteDeviceGroupByGroupId(@RequestParam("groupId") Long groupId) {
        deviceGroupService.deleteDeviceGroupByGroupId(groupId);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "根据设备ID,删除设备分组关系")
    @Parameter(name = "deviceId", description = "设备ID", required = true)
    @DeleteResource(path = "/iot/device/group/delete-by-device-id")
    public ResponseData<Boolean> deleteDeviceGroupByDeviceId(@RequestParam("deviceId") Long deviceId) {
        deviceGroupService.deleteDeviceGroupByDeviceId(deviceId);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除设备分组关系")
    @Parameters({
            @Parameter(name = "groupId", description = "设备分组ID", required = true),
            @Parameter(name = "deviceId", description = "设备ID", required = true)
    })
    @DeleteResource(path = "/iot/device/group/delete")
    public ResponseData<Boolean> deleteDeviceGroup(@RequestParam("groupId") Long groupId, @RequestParam("deviceId") Long deviceId) {
        deviceGroupService.deleteDeviceGroup(groupId, deviceId);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "根据设备ID,获取设备分组关系信息")
    @Parameter(name = "deviceId", description = "设备ID", required = true)
    @GetResource(path = "/iot/device/group/get-by-device-id")
    public ResponseData<IotDeviceGroupResp> getDeviceGroupByDeviceId(@RequestParam("deviceId") Long deviceId) {
        IotDeviceGroupDO deviceGroup = deviceGroupService.getDeviceGroupByDeviceId(deviceId);
        return new SuccessResponseData<>(BeanUtil.toBean(deviceGroup, IotDeviceGroupResp.class));
    }

    @Operation(summary = "根据设备分组ID,获取设备分组关系信息集合")
    @Parameter(name = "groupId", description = "设备分组ID", required = true)
    @GetResource(path = "/iot/device/group/get-by-group-id")
    public ResponseData<List<IotDeviceGroupResp>> listDeviceGroupByGroupId(@RequestParam("groupId") Long groupId) {
        List<IotDeviceGroupDO> iotDeviceGroups = deviceGroupService.listDeviceGroupByGroupId(groupId);
        return new SuccessResponseData<>(BeanUtils.toBean(iotDeviceGroups, IotDeviceGroupResp.class));
    }

}
