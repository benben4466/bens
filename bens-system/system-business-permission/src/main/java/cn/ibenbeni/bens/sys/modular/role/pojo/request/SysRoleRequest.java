package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 系统角色封装类
 *
 * @author benben
 * @date 2025/5/3  下午10:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleRequest extends BaseRequest {

    /**
     * 主键id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 显示顺序
     */
    private BigDecimal roleSort;

    /**
     * 数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，31-本公司及以下数据，40-指定部门数据，50-全部数据
     */
    private Integer dataScopeType;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer statusFlag;

    /**
     * 角色类型：10-系统角色，15-业务角色，20-公司角色
     */
    private Integer roleType;

    /**
     * 角色所属公司id，当角色类型为20时传此值
     */
    private Long roleCompanyId;

    /**
     * 角色id集合，用在批量删除
     */
    private Set<Long> roleIdList;

    /**
     * 备注
     */
    private String remark;

}
