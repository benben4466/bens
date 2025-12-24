package cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"dataType"}) // 忽略子类中的 dataType 字段，从而避免重复
public class ThingModelNumericDataSpec extends ThingModelDataSpecs {

    /**
     * 最大值
     */
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "最大值必须为数值类型")
    private String max;

    /**
     * 最小值
     */
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "最小值必须为数值类型")
    private String min;

    /**
     * 步长
     */
    @NotEmpty(message = "步长不能为空")
    @Pattern(regexp = "^-?\\d+(\\.\\d+)?$", message = "步长必须为数值类型")
    private String step;

    /**
     * 精度
     * <p>dataType=float 或 double 时可选传入</p>
     */
    private String precise;

    /**
     * 默认值, 可传入用于存储的默认值
     */
    private String defaultValue;

    /**
     * 单位的符号
     */
    private String unit;

    /**
     * 单位的名称
     */
    private String unitName;

}
