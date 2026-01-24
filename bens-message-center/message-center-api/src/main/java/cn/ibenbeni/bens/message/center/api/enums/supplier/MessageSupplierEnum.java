package cn.ibenbeni.bens.message.center.api.enums.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 供应商枚举
 */
@Getter
@AllArgsConstructor
public enum MessageSupplierEnum {

    // 无供应商 (本系统实现)
    DEFAULT_SUPPLIER(0, "无供应商"),
    ALI_SUPPLIER(10, "阿里供应商"),
    TENCENT_SUPPLIER(20, "腾讯供应商"),

    ;

    private final Integer code;

    private final String desc;

}
