package cn.ibenbeni.bens.file.api;

import cn.ibenbeni.bens.file.api.enums.FileLocationEnum;

import java.io.InputStream;

/**
 * 文件操作API
 *
 * @author: benben
 * @time: 2025/6/22 上午9:32
 */
public interface FileOperatorApi {

    /**
     * 初始化客户端
     */
    void initClient();

    /**
     * 销毁客户端
     */
    void destroyClient();

    /**
     * 获取操作的客户端
     * <p>获取阿里云的客户端com.aliyun.oss.OSS</p>
     */
    Object getClient();

    /**
     * 存储文件
     *
     * @param bucketName 存储桶名称
     * @param key        唯一标识ID
     * @param bytes      文件字节数组
     */
    void storageFile(String bucketName, String key, byte[] bytes);

    /**
     * 存储文件
     *
     * @param bucketName  存储桶名称
     * @param key         唯一标识ID
     * @param inputStream 文件流
     */
    void storageFile(String bucketName, String key, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param key        唯一标识ID
     */
    void deleteFile(String bucketName, String key);

    /**
     * 获取某存储桶中文件的字节数组
     *
     * @param bucketName 存储桶名称
     * @param key        唯一标识ID
     * @return 文件的字节数组
     */
    byte[] getFileBytes(String bucketName, String key);

    /**
     * 获取文件下载地址，生成外网地址
     *
     * @param bucketName 存储桶名称
     * @param key        唯一标识ID
     * @return 外部系统可以直接访问的url
     */
    String getFileUrl(String bucketName, String key);

    /**
     * 获取当前API的文件存储类型
     */
    FileLocationEnum getFileLocationEnum();

}
