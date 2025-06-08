package cn.ibenbeni.bens.sys.modular.role.pojo.response;

import lombok.Data;

import java.util.List;

/**
 * 角色绑定数据范围的响应
 *
 * @author: benben
 * @time: 2025/6/8 下午2:18
 */
@Data
public class RoleBindDataScopeResponse {

    /**
     * 数据范围类型
     * <p>10-仅本人数据，20-本部门数据，30-本部门及以下数据，31-本公司及以下数据，40-指定部门数据，50-全部数据</p>
     */
    private Integer dataScopeType;

    /**
     * 用户拥有的指定部门的组织机构信息ID集合
     */
    // TODO @SimpleFieldFormat(processClass = OrgDetailFormatProcess.class)
    private List<Long> orgIdList;

}
