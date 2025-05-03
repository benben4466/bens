package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.user.SimpleUserDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserInfoDetailDTO;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;

import java.util.List;

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
     * 获取用户真实姓名
     *
     * @param userId 用户ID
     */
    String getUserRealName(Long userId);

    /**
     * 获取用于用户校验的信息
     *
     * @param account 用户账号
     */
    UserValidateDTO getUserLoginValidateDTO(String account);

    /**
     * 更新用户的登录ip和最后登录时间
     *
     * @param userId 用户ID
     * @param ip     IP
     */
    void updateUserLoginInfo(Long userId, String ip);

    /**
     * 获取用户是否是超级管理员
     *
     * @param userId 用户ID
     */
    boolean getUserSuperAdminFlag(Long userId);

    /**
     * 查询所有的用户id集合
     */
    List<Long> queryAllUserIdList();

    /**
     * 判断用户是否存在
     *
     * @param userId 用户ID
     * @return true=存在；false=不存在
     */
    Boolean userExist(Long userId);

    /**
     * 通过用户ID，获取用户基本信息
     *
     * @param userId 用户ID
     */
    UserInfoDetailDTO getUserDetail(Long userId);

    /**
     * 临时锁定用户状态，默认设定1天的自动解锁日期
     *
     * @param account 用户账号
     */
    void lockUserStatus(String account);

}
