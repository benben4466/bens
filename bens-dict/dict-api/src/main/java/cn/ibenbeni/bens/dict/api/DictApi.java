package cn.ibenbeni.bens.dict.api;

import cn.ibenbeni.bens.rule.pojo.dict.SimpleDict;

import java.util.List;

/**
 * 字典API
 * <p>字典模块对外提供的API，方便其他模块直接调用</p>
 *
 * @author: benben
 * @time: 2025/6/13 下午11:55
 */
public interface DictApi {

    /**
     * 根据字典类型编码获取所有的字典
     *
     * @param dictTypeCode 字典类型编码
     */
    List<SimpleDict> getDictDetailsByDictTypeCode(String dictTypeCode);

}
