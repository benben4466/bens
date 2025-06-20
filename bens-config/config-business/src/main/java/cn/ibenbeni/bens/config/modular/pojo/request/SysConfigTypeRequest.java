package cn.ibenbeni.bens.config.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.validator.api.validators.flag.FlagValue;
import cn.ibenbeni.bens.validator.api.validators.unique.TableUniqueValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 参数配置类型封装请求类
 *
 * @author: benben
 * @time: 2025/6/18 下午10:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigTypeRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long configTypeId;

    /**
     * 参数配置类型名称
     */
    @NotBlank(message = "类型名称不能为空", groups = {add.class, edit.class})
    private String configTypeName;

    /**
     * 参数配置类型编码
     */
    @TableUniqueValue(
            message = "参数配置类型编码已存在",
            groups = {BaseRequest.add.class},
            tableName = "sys_config_type",
            columnName = "config_type_code",
            idFieldName = "config_type_id",
            excludeLogicDeleteItems = true
    )
    @NotBlank(message = "编码不能为空", groups = {add.class})
    private String configTypeCode;

    /**
     * 是否为系统参数类型
     * <p>Y=是，N=否</p>
     */
    @FlagValue(message = "是否为系统参数格式错误", groups = {add.class, edit.class})
    @NotBlank(message = "是否为系统参数不能为空", groups = {add.class, edit.class})
    private String sysFlag;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class, edit.class})
    private BigDecimal configTypeSort;

    /**
     * 备注
     */
    private String remark;

}
