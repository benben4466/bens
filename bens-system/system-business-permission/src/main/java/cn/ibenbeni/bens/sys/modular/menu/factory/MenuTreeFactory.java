package cn.ibenbeni.bens.sys.modular.menu.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;

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
    public static void updateSort(List<SysMenuDO> tree, Integer baseNumber) {
        if (baseNumber == null || baseNumber == 0) {
            baseNumber = 1;
        }

        // 当前树的顺序计算逻辑：
        // baseNumber * 100 + 1
        // baseNumber * 100 + 2
        // baseNumber * 100 + 3
        // 以此类推...
        BigDecimal newBaseNumber = new BigDecimal(baseNumber * 100);

        for (SysMenuDO sysMenuDO : tree) {

            // 树形基础值 + 1
            newBaseNumber = newBaseNumber.add(new BigDecimal(1));
            sysMenuDO.setMenuSort(newBaseNumber);

            // 递归修改子树
            List<SysMenuDO> children = sysMenuDO.getChildren();
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
    public static void fillParentId(Long parentMenuId, List<SysMenuDO> menuTreeList) {
        if (ObjectUtil.isEmpty(menuTreeList)) {
            return;
        }

        for (SysMenuDO sysMenuDO : menuTreeList) {

            sysMenuDO.setMenuParentId(parentMenuId);
            // 递归填充
            if (ObjectUtil.isNotEmpty(sysMenuDO.getChildren())) {
                fillParentId(sysMenuDO.getMenuId(), sysMenuDO.getChildren());
            }
        }
    }

    /**
     * 将指定的树形结构，平行展开，添加到指定的参数totalMenuList
     */
    public static void collectTreeTasks(List<SysMenuDO> sysMenuDOTree, List<SysMenuDO> totalMenuList) {
        if (ObjectUtil.isEmpty(sysMenuDOTree)) {
            return;
        }

        for (SysMenuDO sysMenuDO : sysMenuDOTree) {
            totalMenuList.add(sysMenuDO);
            if (ObjectUtil.isNotEmpty(sysMenuDO.getChildren())) {
                collectTreeTasks(sysMenuDO.getChildren(), totalMenuList);
            }
        }
    }

}
