package cn.ibenbeni.bens.sys.modular.org.pojo.response;

import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组织信息响应
 *
 * @author: benben
 * @time: 2025/7/8 下午1:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrOrganizationResp {

    /**
     * 主键
     */
    private Long orgId;

    /**
     * 父ID
     * <p>一级节点父ID是-1</p>
     */
    private Long orgParentId;

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
     * 组织机构类型
     * <p>类型编码: {@link cn.ibenbeni.bens.sys.api.enums.menu.OrganizationTypeEnum}</p>
     */
    private Integer orgType;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系人号码
     */
    private String contactsPhone;

    /**
     * 联系人邮箱
     */
    private String contactsEmail;

    /**
     * 组织状态
     * <p>{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    private Integer statusFlag;

    /**
     * 描述
     */
    private String remark;
    /**
     * 子节点的集合
     */
    private List<HrOrganizationDO> children;

}
