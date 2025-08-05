package cn.ibenbeni.bens.file.api.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 文件存储位置枚举类
 *
 * @author: benben
 * @time: 2025/6/22 上午9:27
 */
@Getter
public enum FileLocationEnum implements ReadableEnum<FileLocationEnum> {

    /**
     * 本地
     */
    LOCAL(1, "本地存储"),

    /**
     * minio服务器
     */
    MINIO(2, "Minio"),

    /**
     * 腾讯云
     */
    TENCENT(3, "腾讯云COS"),

    /**
     * 阿里云
     */
    ALIYUN(4, "阿里云OSS"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(FileLocationEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String platform;

    FileLocationEnum(int code, String platform) {
        this.code = code;
        this.platform = platform;
    }

    public static FileLocationEnum parseToEnum(int code) {
        for (FileLocationEnum fileLocationEnum : FileLocationEnum.values()) {
            if (fileLocationEnum.getCode().equals(code)) {
                return fileLocationEnum;
            }
        }
        return null;
    }

    /**
     * 校验文件存储位置Code是否合法
     *
     * @param locationCode 文件存储位置Code
     * @return true=合法；false=不合法；
     */
    public static boolean validateLocationCode(int locationCode) {
        for (FileLocationEnum fileLocationEnum : FileLocationEnum.values()) {
            if (fileLocationEnum.getCode().equals(locationCode)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object getKey() {
        return code;
    }

    @Override
    public Object getName() {
        return platform;
    }

    @Override
    public FileLocationEnum parseToEnum(String code) {
        return ArrayUtil.firstMatch(item -> item.getCode().equals(Integer.valueOf(code)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
