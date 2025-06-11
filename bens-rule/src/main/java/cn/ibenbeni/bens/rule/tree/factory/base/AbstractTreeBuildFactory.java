package cn.ibenbeni.bens.rule.tree.factory.base;

import java.util.List;

/**
 * 树构建的抽象类，定义构建tree的基本步骤
 *
 * @author: benben
 * @time: 2025/6/10 下午2:18
 */
public interface AbstractTreeBuildFactory<T> {

    /**
     * 树节点构建整体过程
     *
     * @param nodes 被处理的节点集合
     * @return 被处理后的节点集合（带树形结构了）
     */
    List<T> doTreeBuild(List<T> nodes);

}
