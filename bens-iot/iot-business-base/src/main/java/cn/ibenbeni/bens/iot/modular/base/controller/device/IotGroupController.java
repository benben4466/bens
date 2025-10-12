package cn.ibenbeni.bens.iot.modular.base.controller.device;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotGroupDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.device.IotGroupResp;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotGroupService;
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

@Tag(name = "管理后台 - IOT设备分组")
@RestController
public class IotGroupController {

    @Resource
    private IotGroupService groupService;

    @Operation(summary = "创建设备分组")
    @PostResource(path = "/iot/group/create")
    public ResponseData<Long> createGroup(@RequestBody @Valid IotGroupSaveReq saveReq) {
        return new SuccessResponseData<>(groupService.createGroup(saveReq));
    }

    @Operation(summary = "删除设备分组")
    @Parameter(name = "id", description = "设备分组ID", required = true)
    @DeleteResource(path = "/iot/group/delete")
    public ResponseData<Boolean> deleteGroup(@RequestParam("id") Long id) {
        groupService.deleteGroup(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量设备分组")
    @Parameter(name = "ids", description = "设备分组ID集合", required = true)
    @DeleteResource(path = "/iot/group/delete-list")
    public ResponseData<Boolean> deleteGroup(@RequestParam("ids") Set<Long> ids) {
        groupService.deleteGroup(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新设备分组")
    @PutResource(path = "/iot/group/update")
    public ResponseData<Boolean> updateGroup(@RequestBody @Valid IotGroupSaveReq updateReq) {
        groupService.updateGroup(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取设备分组")
    @GetResource(path = "/iot/group/get")
    public ResponseData<IotGroupResp> getGroup(@RequestParam("id") Long id) {
        IotGroupDO group = groupService.getGroup(id);
        return new SuccessResponseData<>(BeanUtil.toBean(group, IotGroupResp.class));
    }

    @Operation(summary = "获取产品信息分页列表")
    @GetResource(path = "/iot/group/page")
    public ResponseData<PageResult<IotGroupResp>> pageGroup(IotGroupPageReq pageReq) {
        PageResult<IotGroupDO> page = groupService.pageGroup(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotGroupResp.class));
    }

}
