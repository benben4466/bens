package cn.ibenbeni.bens.file.api.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.file.api.client.FileClient;
import cn.ibenbeni.bens.file.api.client.FileClientConfig;
import cn.ibenbeni.bens.file.api.client.local.LocalFileClient;
import cn.ibenbeni.bens.file.api.client.local.LocalFileClientConfig;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 文件存储器枚举
 */
@Getter
@AllArgsConstructor
public enum FileStorageEnum implements ReadableEnum<FileStorageEnum> {

    /**
     * 本地存储
     */
    LOCAL(10, LocalFileClientConfig.class, LocalFileClient.class),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(FileStorageEnum::getStorageCode).toArray(Integer[]::new);

    /**
     * 存储编码
     */
    private final Integer storageCode;

    /**
     * 文件客户端配置类
     */
    private final Class<? extends FileClientConfig> configClass;

    /**
     * 文件客户端类
     */
    private final Class<? extends FileClient> clientClass;

    public static FileStorageEnum parseToEnum(Integer storageCode) {
        return ArrayUtil.firstMatch(item -> item.getStorageCode().equals(storageCode), values());
    }

    @Override
    public Object getKey() {
        return storageCode;
    }

    @Override
    public Object getName() {
        return clientClass.getSimpleName();
    }

    @Override
    public FileStorageEnum parseToEnum(String storageCode) {
        return ArrayUtil.firstMatch(item -> item.getStorageCode().equals(Integer.valueOf(storageCode)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
