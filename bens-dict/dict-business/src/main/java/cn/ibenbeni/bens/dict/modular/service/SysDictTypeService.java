package cn.ibenbeni.bens.dict.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.DictTypeApi;
import cn.ibenbeni.bens.dict.modular.entity.SysDictType;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典类型表服务类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:31
 */
public interface SysDictTypeService extends IService<SysDictType>, DictTypeApi {

    /**
     * 新增
     */
    void add(DictTypeRequest dictTypeRequest);

    /**
     * 删除
     */
    void del(DictTypeRequest dictTypeRequest);

    /**
     * 编辑
     */
    void edit(DictTypeRequest dictTypeRequest);

    /**
     * 查询详情
     */
    SysDictType detail(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表
     *
     * @return
     */
    List<SysDictType> findList(DictTypeRequest dictTypeRequest);

    /**
     * 获取字典类型列表(分页)
     */
    PageResult<SysDictType> findPage(DictTypeRequest dictTypeRequest);

}
