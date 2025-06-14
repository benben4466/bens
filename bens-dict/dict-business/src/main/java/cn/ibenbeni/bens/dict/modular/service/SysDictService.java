package cn.ibenbeni.bens.dict.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.DictApi;
import cn.ibenbeni.bens.dict.modular.entity.SysDict;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典服务类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:55
 */
public interface SysDictService extends IService<SysDict>, DictApi {

    /**
     * 新增
     */
    void add(DictRequest dictRequest);

    /**
     * 删除
     */
    void del(DictRequest dictRequest);

    /**
     * 批量删除
     */
    void batchDelete(DictRequest dictRequest);

    /**
     * 删除字典类型下的所有字典
     */
    void delByDictTypeId(Long dictTypeId);

    /**
     * 编辑
     */
    void edit(DictRequest dictRequest);

    /**
     * 查询详情
     */
    SysDict detail(DictRequest dictRequest);

    /**
     * 查询列表
     * <p>
     * 查询条件：字典类型ID，或者字典类型编码
     * </p>
     */
    List<SysDict> findList(DictRequest dictRequest);

    /**
     * 查询列表(分页)
     * <p>询条件：字典类型ID，或者字典类型编码</p>
     */
    PageResult<SysDict> findPage(DictRequest dictRequest);

}
