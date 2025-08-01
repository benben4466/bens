package cn.ibenbeni.bens.file.api.exception.enums;

import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * @author: benben
 * @time: 2025/6/22 上午9:23
 */
@Getter
public enum FileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 附件IDS为空
     */
    FILE_IDS_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "01", "附件IDS为空!"),

    /**
     * 下载的文件中包含私有文件
     */
    SECRET_FLAG_INFO_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "02", "下载的文件中包含私有文件，具体文件为：{}"),

    /**
     * 阿里云文件操作异常
     */
    ALIYUN_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "03", "阿里云文件操作异常，具体信息为：{}"),

    /**
     * 腾讯云文件操作异常
     */
    TENCENT_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "04", "腾讯云文件操作异常，具体信息为：{}"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "05", "本地文件不存在，具体信息为：{}"),

    /**
     * MinIO文件操作异常
     */
    MINIO_FILE_ERROR(RuleConstants.THIRD_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "06", "MinIO文件操作异常，具体信息为：{}"),

    /**
     * 上传文件操作异常
     */
    ERROR_FILE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "07", "上传文件操作异常，具体信息为：{}"),

    /**
     * 文件不存在
     */
    FILE_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "08", "文件不存在"),

    /**
     * 获取文件流错误
     */
    FILE_STREAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "09", "获取文件流异常，具体信息为：{}"),

    /**
     * 下载文件错误
     */
    DOWNLOAD_FILE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "10", "下载文件错误，具体信息为：{}"),

    /**
     * 预览文件异常
     */
    PREVIEW_ERROR_NOT_SUPPORT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "11", "预览文件异常，您预览的文件类型不支持或文件出现错误"),

    /**
     * 预览文件参数存在空值
     */
    PREVIEW_EMPTY_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "12", "预览文件参数存在空值，请求参数为：{}"),

    /**
     * 渲染文件流字节出错
     */
    WRITE_BYTES_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "13", "渲染文件流字节出错，具体信息为：{}"),

    /**
     * 文件id不能为空
     */
    FILE_ID_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "14", "文件ID不能为空!"),

    /**
     * 文件Code不能为空
     */
    FILE_CODE_NOT_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "15", "文件CODE不能为空!"),

    /**
     * 文件不允许被访问
     */
    FILE_DENIED_ACCESS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16", "文件不允许被访问，文件加密等级不符合"),

    /**
     * 文件不允许被访问
     */
    FILE_PERMISSION_DENIED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "17", "文件不允许被访问，请登录后访问"),

    /**
     * 文件配置编码重复
     */
    FILE_CONFIG_CODE_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16", "文件配置编码重复"),

    /**
     * 文件配置不存在
     */
    FILE_CONFIG_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16", "文件配置不存在"),

    /**
     * 主文件配置不允许删除
     */
    FILE_CONFIG_DELETE_FAIL_MASTER(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + FileConstants.FILE_EXCEPTION_STEP_CODE + "16", "主文件配置不允许删除"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FileExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
