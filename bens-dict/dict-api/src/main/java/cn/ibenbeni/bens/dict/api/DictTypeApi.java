package cn.ibenbeni.bens.dict.api;

/**
 * 字典类型的API
 *
 * @author: benben
 * @time: 2025/6/13 下午11:32
 */
public interface DictTypeApi {

    /**
     * 通过字典类型的编码，获取到字典类型的ID
     *
     * @param dictTypeCode 字典类型编码
     * @return 字典类型的ID
     */
    Long getDictTypeIdByDictTypeCode(String dictTypeCode);

}
