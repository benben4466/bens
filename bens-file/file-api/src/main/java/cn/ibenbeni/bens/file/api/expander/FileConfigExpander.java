package cn.ibenbeni.bens.file.api.expander;

import cn.ibenbeni.bens.config.api.context.ConfigContext;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.file.api.enums.FileLocationEnum;
import cn.ibenbeni.bens.file.api.pojo.prop.LocalFileProp;
import cn.ibenbeni.bens.rule.constants.RuleConstants;

/**
 * 文件相关的配置获取
 *
 * @author: benben
 * @time: 2025/6/22 下午2:23
 */
public class FileConfigExpander {

    /**
     * 获取文件默认存储位置
     * {@link cn.ibenbeni.bens.file.api.enums.FileLocationEnum}
     */
    public static FileLocationEnum getFileDefaultStorageLocation() {
        Integer sysFileDefaultStorageLocation = ConfigContext.me().getSysConfigValueWithDefault("SYS_FILE_DEFAULT_STORAGE_LOCATION", Integer.class, FileConstants.SYS_FILE_DEFAULT_STORAGE_LOCATION);
        if (!FileLocationEnum.validateLocationCode(sysFileDefaultStorageLocation)) {
            sysFileDefaultStorageLocation = FileConstants.SYS_FILE_DEFAULT_STORAGE_LOCATION;
        }
        return FileLocationEnum.parseToEnum(sysFileDefaultStorageLocation);
    }

    /**
     * 获取默认存储桶名称
     */
    public static String getDefaultBucket() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_FILE_DEFAULT_BUCKET", String.class, FileConstants.DEFAULT_BUCKET_NAME);
    }

    /**
     * 获取服务部署的机器host，例如http://localhost
     * <p>如：http://localhost</p>
     */
    public static String getServerDeployHost() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_SERVER_DEPLOY_HOST", String.class, RuleConstants.DEFAULT_SERVER_DEPLOY_HOST);
    }

    /**
     * 本地文件存储位置（linux）
     */
    public static String getLocalFileSavePathLinux() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOCAL_FILE_SAVE_PATH_LINUX", String.class, new LocalFileProp().getLocalFileSavePathLinux());
    }

    /**
     * 本地文件存储位置（windows）
     */
    public static String getLocalFileSavePathWindows() {
        return ConfigContext.me().getSysConfigValueWithDefault("SYS_LOCAL_FILE_SAVE_PATH_WINDOWS", String.class, new LocalFileProp().getLocalFileSavePathWin());
    }

}
