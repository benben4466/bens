package cn.ibenbeni.bens.sys.modular.org.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
public class OrganizationSaveReq {

    /**
     * 机构ID
     */
    @Schema(description = "组织ID", example = "10")
    private Long orgId;

    /**
     * 父ID
     * <p>一级节点父ID是-1</p>
     * <p>若未选择，前端默认-1</p>
     */
    @Schema(description = "父组织ID", example = "9")
    private Long orgParentId;

    /**
     * 组织名称
     */
    @Schema(description = "组织名称", example = "笨笨组织", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "组织名称不能为空")
    @Size(max = 30, message = "组织名称长度不能超过30个字符")
    private String orgName;

    /**
     * 组织机构简称
     */
    @Schema(description = "组织简称", example = "笨笨")
    private String orgShortName;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码", example = "benben_org" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "组织编码不能为空")
    private String orgCode;

    /**
     * 排序
     */
    @Schema(description = "组织显示排序", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "组织排序不能为空")
    private BigDecimal orgSort;

    /**
     * 组织机构类型
     * <p>类型编码: {@link cn.ibenbeni.bens.sys.api.enums.menu.OrganizationTypeEnum}</p>
     */
    @Schema(description = "组织类型", example = "1")
    @NotNull(message = "组织机构类型不能为空")
    private Integer orgType;

    /**
     * 税号
     */
    @Schema(description = "组织税号", example = "91110108551385082Q")
    private String taxNo;

    /**
     * 联系人
     */
    @Schema(description = "组织联系人", example = "笨")
    private String contacts;

    /**
     * 联系人号码
     */
    @Schema(description = "组织联系人号码", example = "12312345671")
    private String contactsPhone;

    /**
     * 联系人邮箱
     */
    @Schema(description = "组织联系人邮箱", example = "ibenbeni@163.com")
    private String contactsEmail;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @Schema(description = "组织状态", example = "1" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "组织状态不能为空")
    private Integer statusFlag;

    /**
     * 描述
     */
    @Schema(description = "组织描述", example = "我是好人")
    private String remark;

}
