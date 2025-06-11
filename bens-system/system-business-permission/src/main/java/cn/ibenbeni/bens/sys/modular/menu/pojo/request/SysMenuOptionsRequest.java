package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "主键不能为空", groups = {edit.class, delete.class})
    private Long menuOptionId;

    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空", groups = {add.class, edit.class})
    private Long menuId;

    /**
     * 功能或操作的名称
     */
    @NotBlank(message = "功能或操作的名称不能为空", groups = {add.class, edit.class})
    private String optionName;

    /**
     * 功能或操作的编码
     */
    @NotBlank(message = "功能或操作的编码不能为空", groups = {add.class, edit.class})
    private String optionCode;

}
