package cn.ibenbeni.bens.sys.modular.role.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;

/**
 * 判断当前用户的所有角色，是否包含了被操作的角色，并且被操作角色的菜单功能ID包含了禁用修改权限的菜单功能ID
 *
 * @author: benben
 * @time: 2025/6/10 下午9:49
 */
public class AssertAssignUtil {

    /**
     * 修改权限的菜单功能ID TODO 暂时为-1L
     */
    public static final Long DISABLED_MENU_OPTIONS = -1L;

    /**
     * 执行判断过程，并增加筛选条件
     * <p>存在两种情况：若不排除修改权限的菜单功能ID，用户取消掉的话，就进入不了功能权限页面。
     *      1. 当前用户修改自己角色的功能权限，则排除修改权限的菜单功能ID。
     *      2. 当前用户修改其他角色的功能权限，则不排除修改权限的菜单功能ID。
     * </p>
     *
     * @param roleId  被操作的角色ID
     * @param wrapper 拼接条件
     */
    public static void assertAssign(Long roleId, LambdaQueryWrapper<SysRoleMenuOptions> wrapper) {
        // 获取当前登录用户的所有角色列表
        SysUserRoleServiceApi sysUserRoleServiceApi = SpringUtil.getBean(SysUserRoleServiceApi.class);
        List<Long> userRoleIdList = sysUserRoleServiceApi.getUserRoleIdList(LoginContext.me().getLoginUser().getUserId());
        if (CollUtil.isEmpty(userRoleIdList)) {
            return;
        }
        if (userRoleIdList.contains(roleId)) {
            wrapper.notIn(SysRoleMenuOptions::getMenuOptionId, DISABLED_MENU_OPTIONS);
        }
    }

}
