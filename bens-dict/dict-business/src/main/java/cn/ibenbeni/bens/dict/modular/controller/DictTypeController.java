package cn.ibenbeni.bens.dict.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.modular.entity.SysDictType;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeRequest;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类型管理
 *
 * @author: benben
 * @time: 2025/6/14 下午1:44
 */
@RestController
public class DictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 新增字典类型
     */
    @PostMapping("/dictType/add")
    public ResponseData<?> add(@RequestBody @Validated(DictTypeRequest.add.class) DictTypeRequest dictTypeRequest) {
        sysDictTypeService.add(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除字典类型
     */
    @PostMapping("/dictType/delete")
    public ResponseData<?> delete(@RequestBody @Validated(DictTypeRequest.delete.class) DictTypeRequest dictTypeRequest) {
        sysDictTypeService.del(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑字典类型
     */
    @PostMapping("/dictType/edit")
    public ResponseData<?> edit(@RequestBody @Validated(DictTypeRequest.edit.class) DictTypeRequest dictTypeRequest) {
        sysDictTypeService.edit(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取字典类型详情
     */
    @GetMapping("/dictType/detail")
    public ResponseData<SysDictType> detail(@Validated(BaseRequest.detail.class) DictTypeRequest dictTypeRequest) {
        SysDictType detail = sysDictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取字典类型列表
     */
    @GetMapping("/dictType/list")
    public ResponseData<List<SysDictType>> list(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(sysDictTypeService.findList(dictTypeRequest));
    }

    /**
     * 获取字典类型列表(分页)
     */
    @GetMapping("/dictType/page")
    public ResponseData<PageResult<SysDictType>> page(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(sysDictTypeService.findPage(dictTypeRequest));
    }

}
