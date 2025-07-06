package cn.ibenbeni.bens.sys.api.pojo.role;

import cn.ibenbeni.bens.rule.tree.factory.base.AbstractTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 角色绑定权限界面的响应封装
 * <p>本结构是个树形结构，第1层级是应用，第2层级是应用下的菜单（菜单只显示最子节点），第3层级是菜单下的功能options</p>
 * <p>本程序无应用，因此第1层为菜单，第2层为菜单下功能</p>
 *
 * @author: benben
 * @time: 2025/6/9 下午10:05
 */
@Data
public class RoleBindPermissionItem implements AbstractTreeNode<RoleBindPermissionItem> {

    /**
     * 节点ID
     * <p>可以是菜单ID和按钮ID</p>
     */
    private Long nodeId;

    /**
     * 父级节点ID
     * <p>菜单作为顶级点，则为-1</p>
     */
    private Long nodeParentId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型
     * <p>1-应用，2-菜单，3-功能，-1-所有权限</p>
     * <p>本程序无应用，因此2-菜单，3-功能，-1-所有权限</p>
     */
    private Integer permissionNodeType;

    /**
     * 是否选择
     * <p>已拥有的是true</p>
     */
    private Boolean checked;

    /**
     * 子节点集合
     */
    private List<RoleBindPermissionItem> children;

    public RoleBindPermissionItem() {
    }

    public RoleBindPermissionItem(Long nodeId, Long parentId, String nodeName, Integer permissionNodeType, Boolean checked) {
        this.nodeId = nodeId;
        this.nodeParentId = parentId;
        this.nodeName = nodeName;
        this.permissionNodeType = permissionNodeType;
        this.checked = checked;
    }

    @Override
    public String getNodeId() {
        if (this.nodeId == null) {
            return "";
        }
        return this.nodeId.toString();
    }

    @Override
    public String getNodeParentId() {
        if (this.nodeParentId == null) {
            return "";
        }
        return this.nodeParentId.toString();
    }

    @Override
    public void setChildrenNodes(List<RoleBindPermissionItem> childrenNodes) {
        this.children = childrenNodes;
    }

}
