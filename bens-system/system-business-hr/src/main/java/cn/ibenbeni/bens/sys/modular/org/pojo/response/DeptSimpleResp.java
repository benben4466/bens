package cn.ibenbeni.bens.sys.modular.org.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织精简信息响应
 *
 * @author: benben
 * @time: 2025/7/8 上午10:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptSimpleResp {

    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织编号
     */
    private String orgCode;

    /**
     * 组织父级ID
     */
    private Long orgParentId;

}
