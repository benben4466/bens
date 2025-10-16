package cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel;

import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotDataSpecsDataTypeEnum;
import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotThingModelAccessModeEnum;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType.ThingModelDataSpecs;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * IOT-物模型的属性
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThingModelProperty {

    /**
     * 属性标识符
     */
    @NotBlank(message = "属性标识符不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{0,31}$", message = "属性标识符只能由字母、数字和下划线组成，必须以字母开头，长度不超过 32 个字符")
    private String identifier;

    /**
     * 属性名称
     */
    @NotEmpty(message = "属性名称不能为空")
    private String name;

    /**
     * 云端对属性操作类型
     */
    @NotEmpty(message = "操作类型不能为空")
    @InEnum(IotThingModelAccessModeEnum.class)
    private String accessMode;

    /**
     * 是否是标准品类的必选服务
     */
    private Boolean required;

    /**
     * 数据类型
     * <p>枚举: {@link IotDataSpecsDataTypeEnum}</p>
     */
    @NotBlank(message = "数据类型不能为空")
    private String dataType;

    /**
     * 数据规范
     * <p>非列型（int、float、double、text、date、array）数据存储在 dataSpecs 中</p>
     */
    private ThingModelDataSpecs dataSpecs;

    /**
     * 数据规范
     * <p>列型（enum、bool、struct）数据存储在 dataSpecsList 中</p>
     */
    private List<ThingModelDataSpecs> dataSpecsList;

}
