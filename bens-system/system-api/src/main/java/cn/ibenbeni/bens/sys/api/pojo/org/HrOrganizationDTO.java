package cn.ibenbeni.bens.sys.api.pojo.org;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 组织机构信息
 *
 * @author benben
 * @date 2025/5/27  下午9:10
 */
@Data
public class HrOrganizationDTO {

    /**
     * 主键
     */
    private Long orgId;

    /**
     * 父id
     */
    private Long orgParentId;

    /**
     * 父ids
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

}
