package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单下的功能操作封装类
 *
 * @author: benben
 * @time: 2025/6/2 上午9:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuOptionsRequest extends BaseRequest {

    /**
     * 主键
     */
    private Long menuOptionId;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 功能或操作的名称
     */
    private String optionName;

    /**
     * 功能或操作的编码
     */
    private String optionCode;

}
