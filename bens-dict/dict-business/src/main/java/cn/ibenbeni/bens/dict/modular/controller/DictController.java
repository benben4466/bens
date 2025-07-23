package cn.ibenbeni.bens.dict.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.modular.entity.SysDict;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictRequest;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
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
 * 字典详情管理
 *
 * @author: benben
 * @time: 2025/6/14 下午1:39
 */
@RestController
public class DictController {

    @Resource
    private SysDictService sysDictService;

    /**
     * 添加字典条目
     */
    @PostMapping("/dict/add")
    public ResponseData<?> add(@RequestBody @Validated DictRequest dictRequest) {
        sysDictService.add(dictRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除字典条目
     */
    @PostMapping("/dict/delete")
    public ResponseData<?> delete(@RequestBody @Validated DictRequest dictRequest) {
        sysDictService.del(dictRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除字典条目
     */
    @PostMapping("/dict/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody @Validated DictRequest dictRequest) {
        sysDictService.batchDelete(dictRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑字典
     */
    @PostMapping("/dict/edit")
    public ResponseData<?> edit(@RequestBody @Validated DictRequest dictRequest) {
        sysDictService.edit(dictRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取字典详情
     */
    @GetMapping("/dict/detail")
    public ResponseData<SysDict> detail(@Validated DictRequest dictRequest) {
        SysDict detail = sysDictService.detail(dictRequest);
        return new SuccessResponseData<>(detail);
    }

    /**
     * 获取字典列表
     * <p>查询条件：字典类型ID，或者字典类型编码</p>
     */
    @GetMapping("/dict/list")
    public ResponseData<List<SysDict>> list(DictRequest dictRequest) {
        return new SuccessResponseData<>(sysDictService.findList(dictRequest));
    }

    /**
     * 获取字典列表(分页)
     * <p>查询条件：字典类型ID，或者字典类型编码</p>
     */
    @GetMapping("/dict/page")
    public ResponseData<PageResult<SysDict>> page(DictRequest dictRequest) {
        return new SuccessResponseData<>(sysDictService.findPage(dictRequest));
    }

}
