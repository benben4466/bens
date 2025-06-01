package cn.ibenbeni.bens.sys.modular.menu.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单树的相关信息创建工厂
 * <p>用在更新菜单树的结构接口上</p>
 *
 * @author: benben
 * @time: 2025/6/1 下午4:13
 */
public class MenuTreeFactory {

    /**
     * 更新菜单的排序
     *
     * @param tree       被更新的菜单树
     * @param baseNumber 初值，整棵树基于这个初值进行计算顺序，可以传最小传1，如果传0，则当1处理
     */
    public static void updateSort(List<SysMenu> tree, Integer baseNumber) {
        if (baseNumber == null || baseNumber == 0) {
            baseNumber = 1;
        }

        // 当前树的顺序计算逻辑：
        // baseNumber * 100 + 1
        // baseNumber * 100 + 2
        // baseNumber * 100 + 3
        // 以此类推...
        BigDecimal newBaseNumber = new BigDecimal(baseNumber * 100);

        for (SysMenu sysMenu : tree) {

            // 树形基础值 + 1
            newBaseNumber = newBaseNumber.add(new BigDecimal(1));
            sysMenu.setMenuSort(newBaseNumber);

            // 递归修改子树
            List<SysMenu> children = sysMenu.getChildren();
            if (children != null && children.size() > 0) {
                updateSort(children, newBaseNumber.intValue());
            }
        }
    }

    /**
     * 填充菜单节点的父级id
     *
     * @param parentMenuId 父节点ID
     * @param menuTreeList 菜单树
     */
    public static void fillParentId(Long parentMenuId, List<SysMenu> menuTreeList) {
        if (ObjectUtil.isEmpty(menuTreeList)) {
            return;
        }

        for (SysMenu sysMenu : menuTreeList) {

            sysMenu.setMenuParentId(parentMenuId);
            // 递归填充
            if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                fillParentId(sysMenu.getMenuId(), sysMenu.getChildren());
            }
        }
    }

    /**
     * 将指定的树形结构，平行展开，添加到指定的参数totalMenuList
     */
    public static void collectTreeTasks(List<SysMenu> sysMenuTree, List<SysMenu> totalMenuList) {
        if (ObjectUtil.isEmpty(sysMenuTree)) {
            return;
        }

        for (SysMenu sysMenu : sysMenuTree) {
            totalMenuList.add(sysMenu);
            if (ObjectUtil.isNotEmpty(sysMenu.getChildren())) {
                collectTreeTasks(sysMenu.getChildren(), totalMenuList);
            }
        }
    }

}
