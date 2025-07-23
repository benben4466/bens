package cn.ibenbeni.bens.sys.modular.org.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询组织列表请求参数
 * <p>查询条件：组织名称、组织状态</p>
 *
 * @author: benben
 * @time: 2025/7/7 下午4:12
 */
@Data
public class OrgListReq {

    /**
     * 组织父级ID
     * <p>树是懒加载，因此通过此参数获取某节点下所有组织</p>
     * <p>此参数为空，默认查询所有一级节点</p>
     */
    @Schema(description = "组织父级ID", example = "9")
    private Long orgParentId;

    /**
     * 组织名称
     */
    @Schema(description = "组织名称", example = "笨笨组织")
    private String orgName;

    /**
     * 组织状态
     * <p>{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "组织状态", example = "1")
    private Integer statusFlag;

}
