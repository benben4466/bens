package cn.ibenbeni.bens.rule.tree.factory;

import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.rule.tree.factory.base.AbstractTreeBuildFactory;
import cn.ibenbeni.bens.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认递归工具类，用于遍历有父子关系的节点，例如菜单树，字典树等等
 *
 * @author: benben
 * @time: 2025/6/10 下午2:17
 */
@Data
@NoArgsConstructor
public class DefaultTreeBuildFactory<T extends AbstractTreeNode<T>> implements AbstractTreeBuildFactory<T> {

    @Override
    public List<T> doTreeBuild(List<T> nodes) {
        // 将每个节点的构造一个子树
        for (T treeNode : nodes) {
            this.buildChildNodes(nodes, treeNode, new ArrayList<>());
        }

        // 只保留上级是根节点的节点，也就是只留下所有一级节点
        ArrayList<T> results = new ArrayList<>();
        for (T node : nodes) {
            if (node.getNodeParentId().equals(TreeConstants.DEFAULT_PARENT_ID.toString())) {
                results.add(node);
            }
        }

        return results;
    }

    /**
     * 查询子节点的集合
     *
     * @param totalNodes     所有节点的集合
     * @param node           被查询节点的ID
     * @param childNodeLists 被查询节点的子节点集合
     */
    protected void buildChildNodes(List<T> totalNodes, T node, List<T> childNodeLists) {
        if (totalNodes == null || node == null) {
            return;
        }

        List<T> nodeSubLists = this.getSubChildsLevelOne(totalNodes, node);
        if (!nodeSubLists.isEmpty()) {
            for (T nodeSub : nodeSubLists) {
                this.buildChildNodes(totalNodes, nodeSub, new ArrayList<>());
            }
        }

        childNodeLists.addAll(nodeSubLists);
        node.setChildrenNodes(childNodeLists);
    }

    /**
     * 获取某节点的子一级节点集合
     *
     * @param list 所有节点的集合
     * @param node 某节点
     */
    protected List<T> getSubChildsLevelOne(List<T> list, T node) {
        List<T> nodeList = new ArrayList<>();
        for (T nodeItem : list) {
            if (nodeItem.getNodeParentId().equals(node.getNodeId())) {
                nodeList.add(nodeItem);
            }
        }
        return nodeList;
    }

}
