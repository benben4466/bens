package cn.ibenbeni.bens.sys.modular.menu.enums.exception;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 系统菜单异常相关枚举
 *
 * @author: benben
 * @time: 2025/6/1 上午11:25
 */
@Getter
public enum SysMenuExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    MENU_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10001", "查询结果不存在"),

    /**
     * 菜单编码全局唯一，不能重复，请更换编码
     */
    MENU_CODE_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10002", "菜单编码全局唯一，不能重复，请更换编码"),

    /**
     * 路由地址不能为空
     */
    URL_CANT_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10003", "路由地址不能为空"),

    /**
     * 组件代码路径不能为空
     */
    COMPONENT_PATH_CANT_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10004", "组件代码路径不能为空"),

    /**
     * 是否隐藏不能为空
     */
    HIDDEN_FLAG_CANT_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10005", "是否隐藏不能为空"),

    /**
     * 链接地址不能为空
     */
    LINK_URL_CANT_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "链接地址不能为空"),

    /**
     * 参数不能为空
     */
    PARAM_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10007", "参数不能为空"),

    /**
     * 不能设置自己为父菜单
     */
    MENU_PARENT_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10008", "不能设置自己为父菜单"),

    /**
     * 父菜单不存在
     */
    MENU_PARENT_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10009", "父菜单不存在"),

    /**
     * 父菜单的类型必须是目录或者菜单
     */
    MENU_PARENT_NOT_DIR_OR_MENU(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10010", "父菜单的类型必须是目录或者菜单"),

    /**
     * 已经存在该名字的菜单
     */
    MENU_NAME_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10011", "已经存在该名字的菜单"),

    /**
     * 存在子菜单,无法删除
     */
    MENU_CHILDREN_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10012", "存在子菜单,无法删除"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysMenuExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
