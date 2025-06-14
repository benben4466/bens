package cn.ibenbeni.bens.dict.api.enums;

import lombok.Getter;

/**
 * 字典类型的分类枚举
 *
 * @author: benben
 * @time: 2025/6/14 下午1:21
 */
@Getter
public enum DictTypeClassEnum {

    /**
     * 业务类型
     */
    BUSINESS_TYPE(1),

    /**
     * 系统类型
     */
    SYSTEM_TYPE(2);

    private final Integer code;

    DictTypeClassEnum(Integer code) {
        this.code = code;
    }

}
