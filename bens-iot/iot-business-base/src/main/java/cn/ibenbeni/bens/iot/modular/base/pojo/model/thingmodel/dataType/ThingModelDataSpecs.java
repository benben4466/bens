package cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType;

import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotDataSpecsDataTypeEnum;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * IOT-物模型数据规范-抽象类
 * <p>@JsonTypeInfo + @JsonSubTypes 实现多态序列化</p>
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "dataType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ThingModelNumericDataSpec.class, name = "int"),
        @JsonSubTypes.Type(value = ThingModelNumericDataSpec.class, name = "float"),
        @JsonSubTypes.Type(value = ThingModelNumericDataSpec.class, name = "double"),
        @JsonSubTypes.Type(value = ThingModelDateOrTextDataSpecs.class, name = "text"),
        @JsonSubTypes.Type(value = ThingModelDateOrTextDataSpecs.class, name = "date"),
        @JsonSubTypes.Type(value = ThingModelBoolOrEnumDataSpecs.class, name = "bool"),
        @JsonSubTypes.Type(value = ThingModelBoolOrEnumDataSpecs.class, name = "enum"),
        @JsonSubTypes.Type(value = ThingModelArrayDataSpecs.class, name = "array"),
        @JsonSubTypes.Type(value = ThingModelStructDataSpecs.class, name = "struct")
})
public abstract class ThingModelDataSpecs {

    /**
     * 数据类型
     * <p>枚举: {@link IotDataSpecsDataTypeEnum}</p>
     */
    private String dataType;

}
