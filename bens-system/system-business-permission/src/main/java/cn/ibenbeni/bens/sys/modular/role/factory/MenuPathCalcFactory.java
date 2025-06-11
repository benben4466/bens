package cn.ibenbeni.bens.sys.modular.role.factory;

import cn.ibenbeni.bens.rule.constants.TreeConstants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 菜单路径计算
 *
 * @author: benben
 * @time: 2025/6/10 下午3:55
 */
public class MenuPathCalcFactory {

    /**
     * 获取指定菜单ID对应的父级ID路径上，所有ID的集合
     * <p>获取指定菜单ID到根节点的菜单ID集合，即菜单ID到根节点的路径上所有ID集合</p>
     *
     * @param menuId            菜单集合
     * @param menuIdParentIdMap 菜单ID父集合；key=menuId、Value=menuParentId
     * @return 返回的ID集合包含参数的menuId
     */
    public static Set<Long> getMenuParentIds(Long menuId, Map<Long, Long> menuIdParentIdMap) {
        Set<Long> uniquePathMenuIds = new HashSet<>();
        uniquePathMenuIds.add(menuId);

        findUniquePathMenuIdsRecursive(menuId, menuIdParentIdMap, uniquePathMenuIds);
        return uniquePathMenuIds;
    }

    /**
     * 递归查询
     */
    private static void findUniquePathMenuIdsRecursive(Long currentMenuId, Map<Long, Long> menuParentMap, Set<Long> uniquePathMenuIds) {
        // 获取当前菜单的父菜单ID
        Long parentId = menuParentMap.get(currentMenuId);
        // 如果父菜单ID为null或者-1，说明已经到达顶级节点，结束递归
        if (parentId == null || TreeConstants.DEFAULT_PARENT_ID.equals(parentId)) {
            return;
        }

        // 将当前菜单ID加入结果集合
        uniquePathMenuIds.add(parentId);

        findUniquePathMenuIdsRecursive(parentId, menuParentMap, uniquePathMenuIds);
    }

}
