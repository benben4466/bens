package cn.ibenbeni.bens.sys.modular.user.pojo.request.org;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组织关系保存请求参数
 *
 * @author: benben
 * @time: 2025/7/8 下午10:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserOrgSaveReq extends BaseRequest {

    /**
     * 组织用户关系ID
     */
    private Long userOrgId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 职位ID
     */
    private Long positionId;

    /**
     * 是否是主部门
     * <p>枚举类型：{@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    private String mainFlag;

    /**
     * 是否启用
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    private Integer statusFlag;

}
