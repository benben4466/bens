package cn.ibenbeni.bens.sys.modular.user.factory;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserDO;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRequest;

/**
 * 用户信息填充，用于创建和修改用户时，添加一些基础信息
 *
 * @author benben
 * @date 2025/5/3  下午7:14
 */
public class SysUserCreateFactory {

    /**
     * 编辑用户时候的用户信息填充
     */
    public static void fillUpdateInfo(SysUserRequest sysUserRequest, SysUserDO sysUser) {
        // 姓名
        sysUser.setRealName(sysUserRequest.getRealName());

        // 性别（M-男，F-女）
        sysUser.setSex(sysUserRequest.getSex());

        // 邮箱
        sysUser.setEmail(sysUserRequest.getEmail());

        // 生日
        if (ObjectUtil.isNotEmpty(sysUserRequest.getBirthday())) {
            sysUser.setBirthday(DateUtil.parse(sysUserRequest.getBirthday()));
        }

        // 手机号码
        if (ObjectUtil.isNotEmpty(sysUserRequest.getPhone())) {
            sysUser.setPhone(sysUserRequest.getPhone());
        }

        // 头像
        if (ObjectUtil.isNotEmpty(sysUserRequest.getAvatar())) {
            sysUser.setAvatar(sysUserRequest.getAvatar());
        }
    }

}
