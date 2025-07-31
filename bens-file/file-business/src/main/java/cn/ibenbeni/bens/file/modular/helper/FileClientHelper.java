package cn.ibenbeni.bens.file.modular.helper;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.ibenbeni.bens.file.api.client.AbstractFileClient;
import cn.ibenbeni.bens.file.api.client.FileClient;
import cn.ibenbeni.bens.file.api.client.FileClientConfig;
import cn.ibenbeni.bens.file.api.enums.FileStorageEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 文件客户端-Helper类
 */
@Slf4j
public class FileClientHelper {

    /**
     * 客户端缓存
     * <p>Key=客户端编号；Value=客户端实例</p>
     */
    private static final ConcurrentMap<String, AbstractFileClient<?>> clientMap = new ConcurrentHashMap<>();

    public static FileClient getFileClient(String fileConfigCode) {
        AbstractFileClient<?> client = clientMap.get(fileConfigCode);
        if (client == null) {
            log.error("[getFileClient][配置ID({}) 找不到客户端]", fileConfigCode);
        }
        return client;
    }

    public static void removeFileClient(String fileConfigCode) {
        clientMap.remove(fileConfigCode);
    }

    /**
     * 创建文件文件客户端
     *
     * @param fileConfigId   文件客户端配置ID
     * @param fileConfigCode 文件客户端配置编码
     * @param fileStorage    文件存储器
     * @param fileConfig     文件客户端配置实例
     * @param <Config>       文件客户端配置类型
     */
    @SuppressWarnings("unchecked")
    public static <Config extends FileClientConfig> void createOrUpdateFileClient(Long fileConfigId, String fileConfigCode, Integer fileStorage, Config fileConfig) {
        AbstractFileClient<Config> client = (AbstractFileClient<Config>) clientMap.get(fileConfigId);
        if (client == null) {
            // 根据文件客户端配置创建文件客户端
            client = createFileClient(fileConfigId, fileConfigCode, fileStorage, fileConfig);
            // 初始化
            client.init();
            clientMap.put(client.getClientCode(), client);
        } else {
            // 若存在，则刷新配置
            client.refresh(fileConfig);
        }
    }

    /**
     * 创建文件客户端
     *
     * @param fileConfigId 文件客户端配置ID
     * @param fileStorage  文件存储器
     * @param fileConfig   文件客户端配置实例
     * @param <Config>     文件客户端配置类型
     * @return 文件客户端实例
     */
    @SuppressWarnings("unchecked")
    private static <Config extends FileClientConfig> AbstractFileClient<Config> createFileClient(Long fileConfigId, String fileConfigCode, Integer fileStorage, Config fileConfig) {
        FileStorageEnum fileStorageEnum = FileStorageEnum.parseToEnum(fileStorage);
        Assert.notNull(fileStorageEnum, String.format("文件配置(%s)为空", fileStorageEnum));
        return (AbstractFileClient<Config>) ReflectUtil.newInstance(fileStorageEnum.getClientClass(), fileConfigId, fileConfigCode, fileConfig);
    }

}