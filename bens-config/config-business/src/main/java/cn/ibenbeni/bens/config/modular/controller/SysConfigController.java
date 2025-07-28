package cn.ibenbeni.bens.config.modular.controller;

import cn.ibenbeni.bens.config.modular.entity.SysConfig;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigRequest;
import cn.ibenbeni.bens.config.modular.service.SysConfigService;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置控制器
 *
 * @author: benben
 * @time: 2025/6/19 下午9:52
 */
@RestController
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 添加系统参数配置
     */
    @PostResource(path = "/sysConfig/add")
    public ResponseData<?> add(@RequestBody @Validated SysConfigRequest sysConfigRequest) {
        sysConfigService.add(sysConfigRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除系统参数配置
     */
    @PostResource(path = "/sysConfig/delete")
    public ResponseData<?> delete(@RequestBody @Validated(BaseRequest.delete.class) SysConfigRequest sysConfigRequest) {
        sysConfigService.del(sysConfigRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除系统参数配置
     */
    @PostResource(path = "/sysConfig/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) SysConfigRequest sysConfigRequest) {
        sysConfigService.batchDelete(sysConfigRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑系统参数配置
     */
    @PostResource(path = "/sysConfig/edit")
    public ResponseData<?> edit(@RequestBody @Validated SysConfigRequest sysConfigRequest) {
        sysConfigService.edit(sysConfigRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查询系统参数配置
     */
    @GetResource(path = "/sysConfig/detail")
    public ResponseData<SysConfig> detail(@Validated SysConfigRequest sysConfigRequest) {
        return new SuccessResponseData<>(sysConfigService.detail(sysConfigRequest));
    }

    /**
     * 查询系统参数配置列表
     */
    @GetResource(path = "/sysConfig/list")
    public ResponseData<List<SysConfig>> list(SysConfigRequest sysConfigRequest) {
        return new SuccessResponseData<>(sysConfigService.findList(sysConfigRequest));
    }

    /**
     * 查询系统参数配置列表（分页）
     */
    @GetResource(path = "/sysConfig/page")
    public ResponseData<PageResult<SysConfig>> page(SysConfigRequest sysConfigRequest) {
        return new SuccessResponseData<>(sysConfigService.findPage(sysConfigRequest));
    }

}
