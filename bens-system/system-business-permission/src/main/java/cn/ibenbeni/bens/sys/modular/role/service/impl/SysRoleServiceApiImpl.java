package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.api.SysRoleServiceApi;
import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleDTO;
import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleSaveReqDTO;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleSaveReq;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceApiImpl implements SysRoleServiceApi {

    @Resource
    private SysRoleService roleService;

    public Long createRole(RoleSaveReqDTO saveReq, Integer roleType) {
        return roleService.createRole(BeanUtil.toBean(saveReq, RoleSaveReq.class), roleType);
    }

    @Override
    public List<RoleDTO> list() {
        List<SysRoleDO> roleList = roleService.getRoleList();
        return BeanUtils.toBean(roleList, RoleDTO.class);
    }

}
