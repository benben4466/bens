package cn.ibenbeni.bens.sys.modular.org.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 组织机构信息封装类
 *
 * @author benben
 * @date 2025/5/28  下午9:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HrOrganizationRequest extends BaseRequest {

    /**
     * 主键
     */
    private Long orgId;

    /**
     * 父ID
     * <p>一级节点父id是-1</p>
     */
    private Long orgParentId;

    /**
     * 父ID集合
     */
    private String orgPids;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织机构简称
     */
    private String orgShortName;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 排序
     */
    private BigDecimal orgSort;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    private Integer statusFlag;

    /**
     * 组织机构类型
     * <p>1-公司，2-部门</p>
     */
    private Integer orgType;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 描述
     */
    private String remark;

    /**
     * 组织机构层级
     */
    private Integer orgLevel;

    /**
     * 组织机构ID集合
     * <p>用于批量操作</p>
     */
    private Set<Long> orgIdList;

    /**
     * 是否只查询公司列表
     * <p>true-查询结果只返回公司，false-查询结果返回公司或部门，如果没传这个参数，则都返回</p>
     */
    private Boolean companySearchFlag;

}
