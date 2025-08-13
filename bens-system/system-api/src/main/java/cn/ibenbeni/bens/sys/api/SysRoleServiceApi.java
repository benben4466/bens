package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleDTO;
import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleSaveReqDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色信息相关的Api
 * <p>其他模块调用</p>
 *
 * @author benben
 */
public interface SysRoleServiceApi {

    /**
     * 创建角色
     *
     * @param saveReq  角色信息
     * @param roleType 角色类型
     * @return 角色ID
     */
    Long createRole(@Valid RoleSaveReqDTO saveReq, Integer roleType);

    /**
     * 获取所有角色列表
     */
    List<RoleDTO> list();

}
