package cn.ibenbeni.bens.dict.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 字典类型请求参数封装类
 *
 * @author: benben
 * @time: 2025/6/14 上午11:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeRequest extends BaseRequest {

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    /**
     * 字典类型名称
     */
    @NotBlank(message = "字典类型名称不能为空")
    private String dictTypeName;

    /**
     * 字典类型编码
     */
    @NotBlank(message = "字典类型编码不能为空")
    private String dictTypeCode;

    /**
     * 字典类型编码
     * <p>1=业务类型；2=系统类型</p>
     */
    @NotNull(message = "字典类型不能为空")
    private Integer dictTypeClass;

    /**
     * 状态
     * <p>1=启用，2=禁用</p>
     */
    @NotNull(message = "状态不能为空")
    // TODO @StatusValue
    private Integer statusFlag;

    /**
     * 字典类型排序
     */
    @NotNull(message = "排序不能为空")
    private BigDecimal dictTypeSort;

    /**
     * 备注
     */
    private String remark;

}
