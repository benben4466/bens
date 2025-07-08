package cn.ibenbeni.bens.sys.modular.org.pojo.vo;

import lombok.Data;

/**
 * 组织树查询条件
 *
 * @author: benben
 * @time: 2025/7/8 上午10:48
 */
@Data
public class OrgTreeReqVO {

    /**
     * 组织父级ID
     * <p>树是懒加载，因此通过此参数获取某节点下所有组织</p>
     * <p>此参数为空，默认查询所有一级节点</p>
     */
    private Long orgParentId;

    /**
     * 组织名称
     */
    private String orgName;

}
