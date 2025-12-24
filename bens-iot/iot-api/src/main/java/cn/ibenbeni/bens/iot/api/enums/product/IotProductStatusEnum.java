package cn.ibenbeni.bens.iot.api.enums.product;

import cn.hutool.core.util.NumberUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * IoT-产品状态枚举
 */
@Getter
@AllArgsConstructor
public enum IotProductStatusEnum implements ReadableEnum<IotProductStatusEnum> {

    UNPUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布"),

    ;

    /**
     * 类型
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotProductStatusEnum::getStatus).toArray(Integer[]::new);

    @Override
    public Object getKey() {
        return status;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotProductStatusEnum parseToEnum(String status) {
        if (!NumberUtil.isNumber(status)) {
            return null;
        }

        for (IotProductStatusEnum value : values()) {
            if (value.status.equals(Integer.valueOf(status))) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
