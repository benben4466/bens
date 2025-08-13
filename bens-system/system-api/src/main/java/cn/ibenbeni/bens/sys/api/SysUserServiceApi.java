package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.user.CreateUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.SimpleUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserInfoDetailDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;

import javax.validation.Valid;

/**
 * 用户基础核心业务Api
 *
 * @author benben
 */
public interface SysUserServiceApi {

    /**
     * 获取用户的基本信息
     *
     * @param userId 用户ID
     * @return 简略用户信息
     */
    SimpleUserDTO getUserInfoByUserId(Long userId);

    /**
     * 获取用于用户校验的信息
     *
     * @param account 用户账号
     */
    UserValidateDTO getUserLoginValidateInfo(String account);

    /**
     * 更新用户的登录ip和最后登录时间
     *
     * @param userId 用户ID
     * @param ip     IP
     */
    void updateUserLoginInfo(Long userId, String ip);

    /**
     * 通过用户ID，获取用户基本信息
     *
     * @param userId 用户ID
     */
    UserInfoDetailDTO getUserDetail(Long userId);

    /**
     * 创建用户
     *
     * @return 用户ID
     */
    Long createUser(@Valid CreateUserDTO createUser);

}
