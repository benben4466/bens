package cn.ibenbeni.bens.sys.modular.org.pojo.vo;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 部门创建/修改请求参数
 *
 * @author: benben
 * @time: 2025/7/7 下午3:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationSaveReqVO extends BaseRequest {

    /**
     * 机构ID
     */
    @NotNull(message = "组织ID不能为空", groups = {update.class})
    private Long orgId;

    /**
     * 父ID
     * <p>一级节点父ID是-1</p>
     * <p>若未选择，前端默认-1</p>
     */
    @NotNull(message = "父部门ID不能为空", groups = {create.class, update.class})
    private Long orgParentId;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空", groups = {create.class, update.class})
    @Size(max = 30, message = "组织名称长度不能超过30个字符", groups = {create.class, update.class})
    private String orgName;

    /**
     * 组织机构简称
     */
    private String orgShortName;

    /**
     * 组织编码
     */
    @NotBlank(message = "组织编码不能为空", groups = {create.class, update.class})
    private String orgCode;

    /**
     * 排序
     */
    @NotNull(message = "组织排序不能为空", groups = {create.class, update.class})
    private BigDecimal orgSort;

    /**
     * 组织机构类型
     * <p>类型编码: {@link cn.ibenbeni.bens.sys.api.enums.menu.OrganizationTypeEnum}</p>
     */
    @NotNull(message = "组织机构类型不能为空", groups = {create.class, update.class})
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
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @NotNull(message = "组织状态不能为空", groups = {create.class, update.class})
    private Integer statusFlag;

    /**
     * 描述
     */
    private String remark;

}
