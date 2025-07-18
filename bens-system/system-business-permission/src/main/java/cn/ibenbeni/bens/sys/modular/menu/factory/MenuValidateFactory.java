package cn.ibenbeni.bens.sys.modular.menu.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.sys.api.enums.menu.MenuTypeEnum;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.enums.exception.SysMenuExceptionEnum;
import cn.ibenbeni.bens.sys.modular.menu.mapper.SysMenuMapper;

/**
 * 菜单参数校验
 *
 * @author: benben
 * @time: 2025/6/1 下午3:09
 */
public class MenuValidateFactory {

    /**
     * 校验父菜单是否合法
     *
     * @param parentMenuId 父菜单ID
     * @param childMenuId  子菜单ID
     */
    public static void validateParentMenu(Long parentMenuId, Long childMenuId) {
        if (parentMenuId == null || TreeConstants.DEFAULT_PARENT_ID.equals(parentMenuId)) {
            return;
        }

        // 不能设置自己为自己的父菜单
        if (parentMenuId.equals(childMenuId)) {
            throw new SysException(SysMenuExceptionEnum.MENU_PARENT_ERROR);
        }

        SysMenuMapper sysMenuMapper = SpringUtil.getBean(SysMenuMapper.class);
        SysMenuDO parentMenu = sysMenuMapper.selectById(parentMenuId);
        // 校验父菜单是否存在
        if (parentMenu == null) {
            throw new SysException(SysMenuExceptionEnum.MENU_PARENT_NOT_EXISTED);
        }

        // 菜单类型是目录或菜单类型，才允许添加子菜单
        if (!MenuTypeEnum.DIRECTORY.getCode().equals(parentMenu.getMenuType())
                && !MenuTypeEnum.MENU.getCode().equals(parentMenu.getMenuType())) {
            throw new SysException(SysMenuExceptionEnum.MENU_PARENT_NOT_DIR_OR_MENU);
        }

    }

    /**
     * 校验菜单名称
     * <p>1.相同父菜单ID下，是否存在相同的菜单名称</p>
     *
     * @param parentMenuId  父菜单ID
     * @param childMenuId   子菜单ID
     * @param childMenuName 子菜单名称
     */
    public static void validateMenuName(Long parentMenuId, Long childMenuId, String childMenuName) {
        SysMenuMapper sysMenuMapper = SpringUtil.getBean(SysMenuMapper.class);
        SysMenuDO menu = sysMenuMapper.selectByParentIdAndName(parentMenuId, childMenuName);
        if (menu == null) {
            return;
        }

        // 菜单ID为空，默认重复
        if (childMenuId == null) {
            throw new SysException(SysMenuExceptionEnum.MENU_NAME_DUPLICATE);
        }

        // ID不相等, 说明菜单名称重复
        if (!menu.getMenuId().equals(childMenuId)) {
            throw new SysException(SysMenuExceptionEnum.MENU_NAME_DUPLICATE);
        }
    }

}
