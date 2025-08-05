package cn.ibenbeni.bens.sys.api.constants;

/**
 * Sys模块操作日志常量
 * 目的：统一管理，避免重复
 */
public interface SysLogRecordConstants {

    // region 系统用户

    String SYSTEM_USER_MODULE_NO = "系统用户";
    String SYSTEM_USER_CREATE_SUB_MODULE_NO = "创建用户";
    String SYSTEM_USER_CREATE_SUCCESS = "创建了用户账号:【{{#user.account}}】";
    String SYSTEM_USER_UPDATE_SUB_MODULE_NO = "更新用户";
    String SYSTEM_USER_UPDATE_SUCCESS = "更新了用户【{{#user.account}}】更新字段: {_DIFF{#updateReqVO}}";
    String SYSTEM_USER_DELETE_SUB_MODULE_NO = "删除用户";
    String SYSTEM_USER_DELETE_SUCCESS = "删除了用户【{{#user.account}}】";
    String SYSTEM_USER_UPDATE_PASSWORD_SUB_MODULE_NO = "重置用户密码";
    String SYSTEM_USER_UPDATE_PASSWORD_SUCCESS = "将用户【{{#user.account}}】的密码从【{{#user.password}}】重置为【{{#newPassword}}】";
    // endregion

    // region 参数校验分组

    // endregion

}
