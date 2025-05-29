package cn.ibenbeni.bens.rule.tree.factory.base;

import java.util.List;

/**
 * 树形节点的抽象接口
 *
 * @author benben
 * @date 2025/5/27  下午9:01
 */
public interface AbstractTreeNode<T> {

    /**
     * 获取节点ID
     *
     * @return 节点的ID标识
     */
    String getNodeId();

    /**
     * 获取节点父ID
     *
     * @return 父节点的ID
     */
    String getNodeParentId();

    /**
     * 设置children
     *
     * @param childrenNodes 设置节点的子节点
     */
    void setChildrenNodes(List<T> childrenNodes);

}
