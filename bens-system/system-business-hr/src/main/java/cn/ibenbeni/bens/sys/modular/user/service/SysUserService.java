package cn.ibenbeni.bens.sys.modular.user.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.dict.SimpleDict;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUser;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRequest;
import cn.ibenbeni.bens.sys.modular.user.pojo.response.PersonalInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统用户 服务类
 *
 * @author benben
 */
public interface SysUserService extends IService<SysUser>, SysUserServiceApi {

    /**
     * 新增
     *
     * @param sysUserRequest 请求参数
     */
    void add(SysUserRequest sysUserRequest);

    /**
     * 删除
     *
     * @param sysUserRequest 请求参数
     */
    void del(SysUserRequest sysUserRequest);

    /**
     * 批量删除用户
     *
     * @param sysUserRequest 请求参数
     */
    void batchDel(SysUserRequest sysUserRequest);

    /**
     * 编辑
     *
     * @param sysUserRequest 请求参数
     */
    void edit(SysUserRequest sysUserRequest);

    /**
     * 查询详情
     *
     * @param sysUserRequest 请求参数
     */
    SysUser detail(SysUserRequest sysUserRequest);

    /**
     * 获取列表
     *
     * @param sysUserRequest 请求参数
     * @return List  返回结果
     */
    List<SysUser> findList(SysUserRequest sysUserRequest);

    /**
     * 获取列表（带分页）
     *
     * @param sysUserRequest 请求参数
     * @return PageResult<SysUser> 返回结果
     */
    PageResult<SysUser> findPage(SysUserRequest sysUserRequest);

    /**
     * 修改用户状态
     */
    void updateStatus(SysUserRequest sysUserRequest);

    /**
     * 获取当前用户的个人信息详情
     */
    PersonalInfo getPersonalInfo();

    /**
     * 更新用户信息（一般用于更新个人信息）
     *
     * @param sysUserRequest 请求参数封装
     */
    void editInfo(SysUserRequest sysUserRequest);

    /**
     * 修改个人头像
     */
    void editAvatar(SysUserRequest sysUserRequest);

    /**
     * 修改个人密码
     */
    void editPassword(SysUserRequest sysUserRequest);

    /**
     * 批量获取用户名称
     */
    List<SimpleDict> batchGetName(SysUserRequest sysUserRequest);

    /**
     * 重置用户密码
     */
    void resetPassword(Long userId, String newPassword);

}
