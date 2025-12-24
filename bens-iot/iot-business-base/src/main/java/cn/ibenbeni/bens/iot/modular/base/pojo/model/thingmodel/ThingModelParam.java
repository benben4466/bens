package cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel;

import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotDataSpecsDataTypeEnum;
import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotThingModelParamDirectionEnum;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType.ThingModelDataSpecs;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * IOT-物模型的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThingModelParam {

    /**
     * 参数标识符
     */
    @NotEmpty(message = "参数标识符不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{0,31}$", message = "参数标识符只能由字母、数字和下划线组成，必须以字母开头，长度不超过 32 个字符")
    private String identifier;

    /**
     * 参数名称
     */
    @NotEmpty(message = "参数名称不能为空")
    private String name;

    /**
     * 用于区分输入或输出参数]
     * <p>枚举：{@link IotThingModelParamDirectionEnum}</p>
     */
    @NotEmpty(message = "参数方向不能为空")
    @InEnum(IotThingModelParamDirectionEnum.class)
    private String direction;

    /**
     * 参数值的数据类型，与 dataSpecs 的 dataType 保持一致
     * <p>枚举：{@link IotDataSpecsDataTypeEnum}</p>
     */
    @NotEmpty(message = "数据类型不能为空")
    @InEnum(IotDataSpecsDataTypeEnum.class)
    private String dataType;

    /**
     * 参数值的数据类型（dataType）为非列表型（int、float、double、text、date、array）的数据规范存储在 dataSpecs 中
     */
    private ThingModelDataSpecs dataSpecs;

    /**
     * 参数值的数据类型（dataType）为列表型（enum、bool、struct）的数据规范存储在 dataSpecsList 中
     */
    private List<ThingModelDataSpecs> dataSpecsList;

}
