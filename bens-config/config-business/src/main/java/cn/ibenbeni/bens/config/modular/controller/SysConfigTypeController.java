package cn.ibenbeni.bens.config.modular.controller;

import cn.ibenbeni.bens.config.modular.entity.SysConfigType;
import cn.ibenbeni.bens.config.modular.pojo.request.SysConfigTypeRequest;
import cn.ibenbeni.bens.config.modular.service.SysConfigTypeService;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置类型控制器
 *
 * @author: benben
 * @time: 2025/6/19 上午11:18
 */
@RestController
public class SysConfigTypeController {

    @Resource
    private SysConfigTypeService sysConfigTypeService;

    /**
     * 新增
     */
    @PostMapping("/sysConfigType/add")
    public ResponseData<SysConfigType> add(@RequestBody @Validated SysConfigTypeRequest sysConfigTypeRequest) {
        sysConfigTypeService.add(sysConfigTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除
     */
    @PostMapping("/sysConfigType/delete")
    public ResponseData<SysConfigType> del(@RequestBody @Validated SysConfigTypeRequest sysConfigTypeRequest) {
        sysConfigTypeService.del(sysConfigTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑
     */
    @PostMapping("/sysConfigType/edit")
    public ResponseData<SysConfigType> edit(@RequestBody @Validated SysConfigTypeRequest sysConfigTypeRequest) {
        sysConfigTypeService.edit(sysConfigTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看详情
     */
    @GetMapping("/sysConfigType/detail")
    public ResponseData<SysConfigType> detail(@Validated SysConfigTypeRequest sysConfigTypeRequest) {
        return new SuccessResponseData<>(sysConfigTypeService.detail(sysConfigTypeRequest));
    }

    /**
     * 查看详情列表
     */
    @GetMapping("/sysConfigType/list")
    public ResponseData<List<SysConfigType>> list(SysConfigTypeRequest sysConfigTypeRequest) {
        return new SuccessResponseData<>(sysConfigTypeService.findList(sysConfigTypeRequest));
    }

    /**
     * 查看详情列表（分页）
     */
    @GetMapping("/sysConfigType/page")
    public ResponseData<PageResult<SysConfigType>> page(SysConfigTypeRequest sysConfigTypeRequest) {
        return new SuccessResponseData<>(sysConfigTypeService.findPage(sysConfigTypeRequest));
    }

}
