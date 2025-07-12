package cn.ibenbeni.bens.sys.modular.user.service;

import cn.ibenbeni.bens.sys.api.callback.RemoveOrgCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemovePositionCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveUserCallbackApi;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserOrgDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.org.UserOrgSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户组织机构关联 服务类
 *
 * @author: benben
 * @time: 2025/7/8 下午10:01
 */
public interface SysUserOrgService extends IService<SysUserOrgDO>, RemoveUserCallbackApi, RemoveOrgCallbackApi, RemovePositionCallbackApi {

    /**
     * 创建组织用户关联
     *
     * @return 记录ID
     */
    Long createUserOrg(UserOrgSaveReq req);

    /**
     * 删除组织用户关联
     */
    void deleteUserOrg(Long id);

    /**
     * 批量删除组织用户关联
     *
     * @param idList 组织用户关联ID列表
     */
    void deleteUserOrgList(List<Long> idList);

    /**
     * 修改组织用户关联
     */
    void updateUserOrg(UserOrgSaveReq req);

    /**
     * 更新用户的任职信息
     * <p>更新逻辑: 先删除所有关联，再关联新关系；因此必须存在一个主部门</p>
     *
     * @param userId      用户ID
     * @param userOrgList 用户任职信息
     */
    void updateUserOrg(Long userId, List<SysUserOrgDO> userOrgList);

}
