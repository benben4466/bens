package cn.ibenbeni.bens.rule.tree.buildpids;

/**
 * 构造pids结构需要的实体规范
 *
 * @author: benben
 * @time: 2025/6/1 上午10:45
 */
public interface BasePidBuildModel {

    /**
     * 获取树形节点ID
     */
    String pidBuildNodeId();

    /**
     * 获取树形节点的父级ID
     */
    String pidBuildParentId();

    /**
     * 设置pids结构
     */
    void setPidBuildPidStructure(String pids);

}
