package cn.ibenbeni.bens.dict.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.dict.modular.entity.SysDictTypeDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypePageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeSaveReq;
import cn.ibenbeni.bens.dict.modular.pojo.response.DictTypeResp;
import cn.ibenbeni.bens.dict.modular.pojo.response.DictTypeSimpleResp;
import cn.ibenbeni.bens.dict.modular.service.SysDictTypeService;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 字典类型管理
 *
 * @author: benben
 * @time: 2025/6/14 下午1:44
 */
@Tag(name = "管理后台 - 字典类型")
@RestController
public class DictTypeController {

    @Resource
    private SysDictTypeService dictTypeService;

    @Operation(summary = "创建字典类型")
    @PostResource(path = "/system/dict-type/create")
    public ResponseData<Long> createDictType(@RequestBody @Valid DictTypeSaveReq req) {
        return new SuccessResponseData<>(dictTypeService.createDictType(req));
    }

    @Operation(summary = "删除字典类型")
    @Parameter(name = "id", description = "字典类型ID", required = true, example = "10")
    @DeleteResource(path = "/system/dict-type/delete")
    public ResponseData<Boolean> deleteDictType(@RequestParam("id") Long id) {
        dictTypeService.deleteDictType(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除字典类型")
    @Parameter(name = "ids", description = "字典类型ID列表", required = true)
    @DeleteResource(path = "/system/dict-type/delete-list")
    public ResponseData<Boolean> deleteDictType(@RequestParam("ids") Set<Long> ids) {
        dictTypeService.deleteDictType(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新字典类型")
    @PutResource(path = "/system/dict-type/update")
    public ResponseData<Boolean> updateDictType(@RequestBody @Valid DictTypeSaveReq req) {
        dictTypeService.updateDictType(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "查询字典类型信息")
    @Parameter(name = "id", description = "字典类型ID", required = true, example = "10")
    @GetResource(path = "/system/dict-type/get")
    public ResponseData<DictTypeResp> getDictType(@RequestParam("id") Long id) {
        SysDictTypeDO dictType = dictTypeService.getDictType(id);
        return new SuccessResponseData<>(BeanUtils.toBean(dictType, DictTypeResp.class));
    }

    @Operation(summary = "获得全部字典类型简略信息列表", description = "主要用于前端的下拉选项")
    @GetResource(path = "/system/dict-type/simple-list")
    public ResponseData<List<DictTypeSimpleResp>> getSimpleDictTypeList() {
        List<SysDictTypeDO> dictTypeList = dictTypeService.getDictTypeList();
        return new SuccessResponseData<>(BeanUtils.toBean(dictTypeList, DictTypeSimpleResp.class));
    }

    @Operation(summary = "获得字典类型分页列表")
    @GetResource(path = "/system/dict-type/page")
    public ResponseData<PageResult<DictTypeResp>> getDictTypePage(DictTypePageReq req) {
        PageResult<SysDictTypeDO> dictTypePage = dictTypeService.getDictTypePage(req);
        return new SuccessResponseData<>(DbUtil.toBean(dictTypePage, DictTypeResp.class));
    }

    @Operation(summary = "导出数据类型")
    @GetResource(path = "/system/dict-type/export-excel")
    public void export(HttpServletResponse response, DictTypePageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysDictTypeDO> rows = dictTypeService.getDictTypePage(req).getRows();
        ExcelUtils.write(
                response,
                "字典类型.xls",
                "数据",
                DictTypeResp.class,
                BeanUtils.toBean(rows, DictTypeResp.class)
        );
    }

}
