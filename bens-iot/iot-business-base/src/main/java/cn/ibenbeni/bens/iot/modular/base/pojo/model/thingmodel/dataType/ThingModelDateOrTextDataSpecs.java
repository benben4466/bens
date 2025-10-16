package cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;

/**
 * IoT-物模型数据类型为时间型或文本型的 DataSpec 定义
 * <p>数据类型，取值为 date 或 text</p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"dataType"}) // 忽略子类中的 dataType 字段，从而避免重复
public class ThingModelDateOrTextDataSpecs extends ThingModelDataSpecs {

    @Max(value = 2048, message = "数据长度不能超过 2048")
    private Integer length;

    /**
     * 默认值，可选参数，用于存储默认值
     */
    private String defaultValue;

}
