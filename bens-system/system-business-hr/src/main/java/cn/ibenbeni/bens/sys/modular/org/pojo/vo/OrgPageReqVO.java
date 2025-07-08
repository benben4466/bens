package cn.ibenbeni.bens.sys.modular.org.pojo.vo;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
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
    private String orgName;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织状态
     * <p>{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    private Integer statusFlag;

}
