package cn.ibenbeni.bens.sys.modular.org.pojo.vo;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 组织分页查询参数
 *
 * @author: benben
 * @time: 2025/7/8 上午11:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrgPageReqVO extends PageParam {

    /**
     * 组织名称
     */
    @Schema(description = "组织名称", example = "笨笨组织")
    private String orgName;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码", example = "benben_org")
    private String orgCode;

    /**
     * 组织状态
     * <p>{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "组织状态", example = "1")
    private Integer statusFlag;

}
