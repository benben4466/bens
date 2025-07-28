package cn.ibenbeni.bens.dict.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.dict.modular.entity.SysDictDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictPageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictSaveReq;
import cn.ibenbeni.bens.dict.modular.pojo.response.DictResp;
import cn.ibenbeni.bens.dict.modular.pojo.response.DictSimpleResp;
import cn.ibenbeni.bens.dict.modular.service.SysDictService;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
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
 * 字典详情管理
 *
 * @author: benben
 * @time: 2025/6/14 下午1:39
 */
@Tag(name = "管理后台 - 字典")
@RestController
public class DictController {

    @Resource
    private SysDictService dictService;

    @Operation(summary = "新增字典")
    @PostResource(path = "/system/dict/create")
    public ResponseData<Long> createDictData(@RequestBody @Valid DictSaveReq req) {
        return new SuccessResponseData<>(dictService.createDict(req));
    }

    @Operation(summary = "删除字典")
    @Parameter(name = "id", description = "字典ID", required = true, example = "10")
    @DeleteResource(path = "/system/dict/delete")
    public ResponseData<Boolean> deleteDictData(@RequestParam("id") Long id) {
        dictService.deleteDict(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除字典")
    @Parameter(name = "ids", description = "字典ID", required = true)
    @DeleteResource(path = "/system/dict/delete-list")
    public ResponseData<Boolean> deleteDictList(@RequestParam("ids") Set<Long> ids) {
        dictService.deleteDict(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新字典数据")
    @PutResource(path = "/system/dict/update")
    public ResponseData<Boolean> updateDict(@RequestBody DictSaveReq req) {
        dictService.updateDict(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "查询字典信息")
    @Parameter(name = "id", description = "字典ID", required = true, example = "10")
    @GetResource(path = "/system/dict/get")
    public ResponseData<DictResp> getDict(@RequestParam("id") Long id) {
        return new SuccessResponseData<>(BeanUtils.toBean(dictService.getDict(id), DictResp.class));
    }

    @Operation(summary = "获得全部字典数据列表", description = "一般用于管理后台缓存字典数据在本地")
    @GetResource(path = "/system/dict/simple-list")
    public ResponseData<List<DictSimpleResp>> getSimpleDictList() {
        List<SysDictDO> dictDOList = dictService.listByStatusAndDictTypeCode(StatusEnum.ENABLE.getCode(), null);
        return new SuccessResponseData<>(BeanUtils.toBean(dictDOList, DictSimpleResp.class));
    }

    @Operation(summary = "获得字典分页列表")
    @GetResource(path = "/system/dict/page")
    public ResponseData<PageResult<DictResp>> getDictPage(DictPageReq req) {
        PageResult<SysDictDO> dictPage = dictService.getDictPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(dictPage, DictResp.class));
    }

    @Operation(summary = "导出字典信息")
    @GetResource(path = "/system/dict/export-excel")
    public void export(HttpServletResponse response, DictPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysDictDO> rows = dictService.getDictPage(req).getRows();
        ExcelUtils.write(
                response,
                "字典数据.xls",
                "数据",
                DictResp.class,
                BeanUtils.toBean(rows, DictResp.class)
        );
    }

}
