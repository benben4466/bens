package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 租户套餐请求参数
 *
 * @author: benben
 * @time: 2025/6/30 下午5:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysTenantPackageRequest extends BaseRequest {

    /**
     * 租户套餐编号
     */
    @NotNull(message = "套餐编号不能为空")
    private Long packageId;

    /**
     * 租户套餐名称
     */
    @NotBlank(message = "套餐名称不能为空")
    private String packageName;


    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @NotNull(message = "状态不能为空")
    private Integer statusFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户套餐ID集合
     * <p>批量操作使用</p>
     */
    @NotEmpty(message = "套餐编号集合不能为空", groups = {batchDelete.class})
    private Set<Long> packageIdList;

}
