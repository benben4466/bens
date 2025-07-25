package cn.ibenbeni.bens.sys.api.enums.role;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 *
 * @author: benben
 * @time: 2025/7/1 下午2:52
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    EMPLOYEE("employee", "普通员工"),

    ;

    /**
     * 角色编码
     */
    private final String code;

    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return ObjectUtil.equal(code, SUPER_ADMIN.code);
    }

}
