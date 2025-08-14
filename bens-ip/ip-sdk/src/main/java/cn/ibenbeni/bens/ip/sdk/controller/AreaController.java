package cn.ibenbeni.bens.ip.sdk.controller;

import cn.hutool.core.lang.Assert;
import cn.ibenbeni.bens.ip.sdk.Area;
import cn.ibenbeni.bens.ip.sdk.pojo.response.AreaNodeResp;
import cn.ibenbeni.bens.ip.sdk.util.AreaUtils;
import cn.ibenbeni.bens.ip.sdk.util.IPUtils;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "管理后台 - 地区")
@RestController
public class AreaController {

    @Operation(summary = "获得地区树")
    @GetResource(path = "/system/area/tree")
    public ResponseData<List<AreaNodeResp>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return new SuccessResponseData<>(BeanUtils.toBean(area.getChildren(), AreaNodeResp.class));
    }

    @Operation(summary = "获得IP对应的地区名")
    @Parameter(name = "ip", description = "IP", required = true)
    @GetResource(path = "/system/area/get-name-by-ip")
    public ResponseData<String> getAreaNameByIp(@RequestParam("ip") String ip) {
        String search = IPUtils.getAreaNameByIp(ip);
        return new SuccessResponseData<>(search);
    }

}
