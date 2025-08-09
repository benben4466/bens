package cn.ibenbeni.bens.sys.api.pojo.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门数据权限响应-DTO
 */
@Data
@Builder
@AllArgsConstructor
public class DeptDataPermissionRespDTO {

    /**
     * 是否可看全部数据
     */
    private Boolean all;

    /**
     * 是否可看自己数据
     */
    private Boolean self;

    /**
     * 可看部门的部门ID集合
     */
    private Set<Long> deptIds;

    public DeptDataPermissionRespDTO() {
        this.all = false;
        this.self = false;
        this.deptIds = new HashSet<>();
    }

}
