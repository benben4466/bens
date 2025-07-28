package cn.ibenbeni.bens.dict.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.modular.entity.SysDictType;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeRequest;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostResource(path = "/dictType/add")
    public ResponseData<?> add(@RequestBody @Validated DictTypeRequest dictTypeRequest) {
        sysDictTypeService.add(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除字典类型
     */
    @PostResource(path = "/dictType/delete")
    public ResponseData<?> delete(@RequestBody @Validated DictTypeRequest dictTypeRequest) {
        sysDictTypeService.del(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑字典类型
     */
    @PostResource(path = "/dictType/edit")
    public ResponseData<?> edit(@RequestBody @Validated DictTypeRequest dictTypeRequest) {
        sysDictTypeService.edit(dictTypeRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取字典类型详情
     */
    @GetResource(path = "/dictType/detail")
    public ResponseData<SysDictType> detail(@Validated DictTypeRequest dictTypeRequest) {
        SysDictType detail = sysDictTypeService.detail(dictTypeRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取字典类型列表
     */
    @GetResource(path = "/dictType/list")
    public ResponseData<List<SysDictType>> list(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(sysDictTypeService.findList(dictTypeRequest));
    }

    /**
     * 获取字典类型列表(分页)
     */
    @GetResource(path = "/dictType/page")
    public ResponseData<PageResult<SysDictType>> page(DictTypeRequest dictTypeRequest) {
        return new SuccessResponseData<>(sysDictTypeService.findPage(dictTypeRequest));
    }

}
