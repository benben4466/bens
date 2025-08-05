package cn.ibenbeni.bens.sys.api.enums.role;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 角色枚举
 *
 * @author: benben
 * @time: 2025/7/1 下午2:52
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum implements ReadableEnum<RoleCodeEnum> {

    SUPER_ADMIN("super_admin", "超级管理员"),
    EMPLOYEE("employee", "普通员工"),

    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(RoleCodeEnum::getCode).toArray(String[]::new);

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

    @Override
    public Object getKey() {
        return code;
    }

    @Override
    public RoleCodeEnum parseToEnum(String code) {
        return ArrayUtil.firstMatch(item -> item.getCode().equals(code), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
