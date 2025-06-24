package cn.ibenbeni.bens.file.api.constants;

/**
 * 文件模块的常量
 *
 * @author benben
 */
public interface FileConstants {

    /**
     * 文件模块的名称
     */
    String FILE_MODULE_NAME = "bens-file";

    /**
     * 系统默认头像的文件路径
     */
    String DEFAULT_AVATAR_FILE_PATH = "DEFAULT:DEFAULT";

    /**
     * 异常枚举的步进值
     */
    String FILE_EXCEPTION_STEP_CODE = "09";

    /**
     * 默认文件存储的位置
     */
    String DEFAULT_BUCKET_NAME = "defaultBucket";

    /**
     * 文件默认存储位置，默认本地存储
     */
    Integer SYS_FILE_DEFAULT_STORAGE_LOCATION = 1;

    /**
     * 文件分割符
     */
    String FILE_POSTFIX_SEPARATOR = ".";

    /**
     * 通用文件预览，通过object名称和bucket名称
     */
    String FILE_PREVIEW_BY_OBJECT_NAME = "/sysFileInfo/previewByObjectName";

    /**
     * 默认头像的文件文件名
     */
    String DEFAULT_AVATAR_FILE_OBJ_NAME = "defaultAvatar.png";

    /**
     * Bens中公共文件预览的接口（一般用在首页背景，首页banner等地方）
     */
    String FILE_PUBLIC_PREVIEW_URL = "/sysFileInfo/public/preview";

}
