package cn.ibenbeni.bens.dict.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.DictTypeApi;
import cn.ibenbeni.bens.dict.modular.entity.SysDictTypeDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypePageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictTypeSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 字典类型表服务类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:31
 */
public interface SysDictTypeService extends IService<SysDictTypeDO>, DictTypeApi {

    /**
     * 创建字典类型
     *
     * @return 字典类型ID
     */
    Long createDictType(DictTypeSaveReq req);

    /**
     * 删除字典类型
     *
     * @param dictTypeId 字典类型ID
     */
    void deleteDictType(Long dictTypeId);

    /**
     * 批量删除字典类型
     *
     * @param dictTypeIdSet 字典类型ID集合
     */
    void deleteDictType(Set<Long> dictTypeIdSet);

    /**
     * 更新字典类型
     */
    void updateDictType(DictTypeSaveReq req);

    /**
     * 获取字典类型信息
     *
     * @param dictTypeId 字典类型ID
     */
    SysDictTypeDO getDictType(Long dictTypeId);

    /**
     * 获取字典类型信息
     *
     * @param dictTypeCode 字典类型编码
     */
    SysDictTypeDO getDictType(String dictTypeCode);

    /**
     * 获取字典类型分页列表
     */
    PageResult<SysDictTypeDO> getDictTypePage(DictTypePageReq req);

    /**
     * 获得所有字典类型列表
     */
    List<SysDictTypeDO> getDictTypeList();

}
