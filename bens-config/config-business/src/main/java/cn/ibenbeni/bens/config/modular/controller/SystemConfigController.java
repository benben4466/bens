package cn.ibenbeni.bens.config.modular.controller;

import cn.ibenbeni.bens.config.modular.pojo.request.ConfigInitReq;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统参数配置控制器
 */
@Tag(name = "管理后台 - 系统参数类型")
@RestController
public class SystemConfigController {

    @Resource
    private SysConfigService configService;

    @Operation(summary = "获取系统配置初始化标识")
    @GetResource(path = "/system/sys-config/init-config-flag")
    public ResponseData<Boolean> getInitConfigFlag() {
        return new SuccessResponseData<>(configService.getInitConfigFlag());
    }

    @Operation(summary = "初始化系统配置")
    @PostResource(path = "/system/sys-config/init-config")
    public ResponseData<Boolean> initConfig(@RequestBody ConfigInitReq req) {
        configService.initConfig(req);
        return new SuccessResponseData<>(true);
    }

}
