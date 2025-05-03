package cn.ibenbeni.bens.rule.pojo.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 只包含id,name,code三个字段的pojo
 * <p>一般用在获取某个业务的下拉列表的返回bean</p>
 * <p>例如，返回用户下拉列表，只需返回用户id和姓名即可</p>
 * <p>例如，返回角色下拉列表，只需返回角色id和角色名称</p>
 *
 * @author benben
 * @date 2025/4/19  下午1:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDict {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

}
