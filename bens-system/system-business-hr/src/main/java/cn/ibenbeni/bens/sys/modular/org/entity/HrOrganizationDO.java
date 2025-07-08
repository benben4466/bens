package cn.ibenbeni.bens.sys.modular.org.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseExpandFieldEntity;
import cn.ibenbeni.bens.rule.tree.factory.base.AbstractTreeNode;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组织机构信息实例类
 *
 * @author benben
 * @date 2025/5/27  下午8:49
 */
@TableName(value = "sys_hr_organization", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class HrOrganizationDO extends BaseExpandFieldEntity implements AbstractTreeNode<HrOrganizationDO> {

    // region 表实体字段

    /**
     * 主键
     */
    @TableId(value = "org_id", type = IdType.ASSIGN_ID)
    private Long orgId;

    /**
     * 父ID
     * <p>一级节点父ID是-1</p>
     */
    @TableField("org_parent_id")
    private Long orgParentId;

    /**
     * 组织名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 组织机构简称
     */
    @TableField("org_short_name")
    private String orgShortName;

    /**
     * 组织编码
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 排序
     */
    @TableField("org_sort")
    private BigDecimal orgSort;

    /**
     * 组织机构类型
     * <p>类型编码: {@link cn.ibenbeni.bens.sys.api.enums.menu.OrganizationTypeEnum}</p>
     */
    @TableField("org_type")
    private Integer orgType;

    /**
     * 税号
     */
    @TableField("tax_no")
    private String taxNo;

    /**
     * 联系人
     */
    @TableField("contacts")
    private String contacts;

    /**
     * 联系人号码
     */
    @TableField("contacts_phone")
    private String contactsPhone;

    /**
     * 联系人邮箱
     */
    @TableField("contacts_email")
    private String contactsEmail;

    /**
     * 状态
     * <p>{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    // endregion

    //-------------------------------非实体字段-------------------------------
    // region 非实体字段

    /**
     * 子节点的集合
     */
    @TableField(exist = false)
    private List<HrOrganizationDO> children;

    // endregion

    @Override
    public String getNodeId() {
        if (this.orgId == null) {
            return null;
        }
        return this.orgId.toString();
    }

    @Override
    public String getNodeParentId() {
        if (this.orgParentId == null) {
            return null;
        }
        return this.orgParentId.toString();
    }

    @Override
    public void setChildrenNodes(List<HrOrganizationDO> childrenNodes) {
        this.children = childrenNodes;
    }

}
