package cn.ibenbeni.bens.sys.modular.user.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserPageReqVO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.UserSaveReqVO;
import cn.ibenbeni.bens.sys.modular.user.pojo.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.ibenbeni.bens.tenant.api.callback.RemoveTenantCallbackApi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统用户 服务类
 *
 * @author benben
 */
public interface SysUserService extends IService<SysUserDO>, RemoveTenantCallbackApi {

    /**
     * 创建用户
     *
     * @return 用户ID
     */
    Long createUser(UserSaveReqVO createReqVO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 批量删除用户
     *
     * @param idList 用户ID列表
     */
    void deleteUserList(List<Long> idList);

    /**
     * 修改用户
     */
    void updateUser(UserSaveReqVO updateReqVO);

    /**
     * 修改用户密码
     *
     * @param id    用户ID
     * @param reqVO 修改密码参数
     */
    void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO);

    /**
     * 修改用户密码
     *
     * @param id       用户ID
     * @param password 修改密码参数
     */
    void updateUserPassword(Long id, String password);

    /**
     * 修改用户状态
     *
     * @param id     用户ID
     * @param status 用户状态
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SysUserDO getUserById(Long id);

    /**
     * 根据用户账号查询用户信息
     *
     * @param account 用户账号
     * @return 用户信息
     */
    SysUserDO getUserByAccount(String account);

    /**
     * 获得用户列表
     *
     * @param idSet 用户ID集合
     * @return 用户列表
     */
    List<SysUserDO> getUserList(Set<Long> idSet);

    /**
     * 获得用户分页列表
     *
     * @param reqVO 分页条件
     * @return 用户分页列表
     */
    PageResult<SysUserDO> getUserPage(UserPageReqVO reqVO);

    /**
     * 判断密码是否匹配
     *
     * @param encryptBefore 未加密密码
     * @param passwordSalt  密码盐
     * @param encryptAfter  已加密密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String encryptBefore, String passwordSalt, String encryptAfter);

}
