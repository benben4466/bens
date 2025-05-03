package cn.ibenbeni.bens.sys.api.enums.user;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.api.exception.enums.UserExceptionEnum;
import lombok.Getter;

/**
 * 用户状态的枚举
 *
 * @author benben
 */
@Getter
public enum UserStatusEnum implements ReadableEnum<UserStatusEnum> {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 冻结
     */
    DISABLE(2, "冻结"),

    /**
     * 临时冻结，这个一般用在密码输入次数超过指定次数后，会被临时冻结
     */
    TEMP_FREEZE(3, "临时冻结");

    private final Integer code;

    private final String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转化为enum
     */
    public static UserStatusEnum toEnum(Integer code) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getCode().equals(code)) {
                return userStatusEnum;
            }
        }
        return null;
    }

    /**
     * 获取code对应的message
     */
    public static String getCodeMessage(Integer code) {
        UserStatusEnum userStatusEnum = toEnum(code);
        if (userStatusEnum != null) {
            return userStatusEnum.getMessage();
        } else {
            return "";
        }
    }

    /**
     * 检查请求参数的状态是否正确
     */
    public static void validateUserStatus(Integer code) {
        if (code == null) {
            throw new ServiceException(UserExceptionEnum.REQUEST_USER_STATUS_EMPTY);
        }
        if (ENABLE.getCode().equals(code) || DISABLE.getCode().equals(code)) {
            return;
        }
        throw new SysException(UserExceptionEnum.REQUEST_USER_STATUS_ERROR, code);
    }


    @Override
    public Object getKey() {
        return this.code;
    }

    @Override
    public Object getName() {
        return this.message;
    }

    @Override
    public UserStatusEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (UserStatusEnum value : UserStatusEnum.values()) {
            if (value.code.equals(Convert.toInt(originValue))) {
                return value;
            }
        }
        return null;
    }

}
