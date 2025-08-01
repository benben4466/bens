package cn.ibenbeni.bens.sys.modular.menu.enums.exception;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 菜单下的功能操作异常相关枚举
 *
 * @author: benben
 * @time: 2025/6/2 上午10:25
 */
@Getter
public enum SysMenuOptionsExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    SYS_MENU_OPTIONS_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10001", "查询结果不存在"),

    /**
     * 功能编码全局唯一，不能重复
     */
    OPTIONS_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10002", "功能编码全局唯一，不能重复"),

    /**
     * 同菜单下功能名称不能重复
     */
    OPTIONS_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10003", "同菜单下功能名称不能重复"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysMenuOptionsExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
