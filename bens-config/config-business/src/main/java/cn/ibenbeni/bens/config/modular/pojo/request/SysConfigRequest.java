package cn.ibenbeni.bens.config.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.validator.api.validators.flag.FlagValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 参数配置封装请求类
 *
 * @author: benben
 * @time: 2025/6/18 上午10:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "ID不能为空")
    private Long configId;

    /**
     * 参数配置类型编码
     */
    @NotBlank(message = "参数配置类型编码不能为空")
    private String configTypeCode;

    /**
     * 参数配置名称
     */
    @NotBlank(message = "参数配置名称不能为空")
    private String configName;

    /**
     * 参数配置编码
     */
    @NotBlank(message = "参数配置编码不能为空")
    private String configCode;

    /**
     * 参数配置值
     */
    @NotBlank(message = "参数配置值不能为空")
    private String configValue;

    /**
     * 是否是系统参数
     * <p>Y=是，N=否</p>
     */
    @NotNull(message = "是否是系统参数不能为空")
    @FlagValue(message = "是否是系统参数格式错误,正确格式应该Y或者N")
    private String sysFlag;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private BigDecimal configSort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 参数配置ID列表
     * <p>用于批量操作</p>
     */
    @NotEmpty(message = "参数配置ID列表不能为空")
    private List<Long> configIdList;

}
