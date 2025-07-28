package cn.ibenbeni.bens.dict.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.dict.api.DictApi;
import cn.ibenbeni.bens.dict.modular.entity.SysDictDO;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictPageReq;
import cn.ibenbeni.bens.dict.modular.pojo.request.DictSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 字典服务类
 *
 * @author: benben
 * @time: 2025/6/13 下午11:55
 */
public interface SysDictService extends IService<SysDictDO>, DictApi {

    /**
     * 创建字典
     *
     * @return 字典ID
     */
    Long createDict(DictSaveReq req);

    /**
     * 删除字典
     *
     * @param dictId 字典ID
     */
    void deleteDict(Long dictId);

    /**
     * 批量删除字典
     *
     * @param dictIdSet 字典ID集合
     */
    void deleteDict(Set<Long> dictIdSet);

    /**
     * 更新字典
     */
    void updateDict(DictSaveReq req);

    /**
     * 获取字典信息
     *
     * @param dictId 字典ID
     */
    SysDictDO getDict(Long dictId);

    /**
     * 获取字典信息
     *
     * @param dictTypeCode 字典类型编码
     * @param dictValue    字典值
     */
    SysDictDO getDict(String dictTypeCode, String dictValue);

    /**
     * 获取指定字典类型编码下字典数量
     *
     * @param dictTypeCode 字典类型编码
     */
    long getDictCountByDictTypeCode(String dictTypeCode);

    /**
     * 获取字典数据列表
     *
     * @param dictTypeCode   字典类型编码
     * @param dictStatusFlag 字典状态
     */
    List<SysDictDO> listByStatusAndDictTypeCode(Integer dictStatusFlag, String dictTypeCode);

    /**
     * 获取字典数据列表
     *
     * @param dictTypeCode 字典类型编码
     */
    List<SysDictDO> listByDictTypeCode(String dictTypeCode);

    /**
     * 获得字典数据分页列表
     */
    PageResult<SysDictDO> getDictPage(DictPageReq req);

    /**
     * 校验字典是否有效
     * <p>
     * 以下情况无效：
     * 1.字典不存在
     * 2.字典被禁用
     * </p>
     *
     * @param dictTypeCode 字典类型编码
     * @param dictValueSet 字典值集合
     */
    void validateDictList(String dictTypeCode, Set<String> dictValueSet);

}
