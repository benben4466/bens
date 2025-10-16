package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IoT-物模型类型枚举
 *
 * @author ahh
 */
@Getter
@AllArgsConstructor
public enum IotDataSpecsDataTypeEnum implements ReadableEnum<IotDataSpecsDataTypeEnum> {

    INT("int", "整型"),
    FLOAT("float", "单精度浮点型"),
    DOUBLE("double", "双精度浮点型"),
    ENUM("enum", "枚举"),
    BOOL("bool", "布尔"),
    TEXT("text", "文本"),
    DATE("date", "时间"),
    STRUCT("struct", "结构体"),
    ARRAY("array", "数组"),

    ;

    /**
     * 数据类型编码
     */
    private final String dataType;

    /**
     * 描述
     */
    private final String description;

    @Override
    public Object getKey() {
        return null;
    }

    @Override
    public Object getName() {
        return null;
    }

    @Override
    public IotDataSpecsDataTypeEnum parseToEnum(String originValue) {
        return null;
    }

    @Override
    public Object[] compareValueArray() {
        return new Object[0];
    }

}
