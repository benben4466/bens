package cn.ibenbeni.bens.dict.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 字典请求参数封装
 *
 * @author: benben
 * @time: 2025/6/14 上午10:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictRequest extends BaseRequest {

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private Long dictId;

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典值
     */
    @NotNull(message = "字典值不能为空")
    private String dictValue;

    /**
     * 字典简称名称
     */
    private String dictShortName;

    /**
     * 字典排序
     */
    @NotNull(message = "排序不能为空")
    private BigDecimal dictSort;

    /**
     * 状态：0=禁用，1=正常
     */
    @NotNull(message = "状态不能为空")
    private Integer statusFlag;

    /**
     * 字典ID集合
     * <p>用于批量操作</p>
     */
    @NotEmpty(message = "字典ID集合不能为空", groups = {batchDelete.class})
    private Set<Long> dictIdList;

    /**
     * 字典类型编码
     * <p>搜索条件</p>
     */
    private String dictTypeCode;

}
