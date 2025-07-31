package cn.ibenbeni.bens.file.api.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 文件客户端配置
 * <p>
 * 使用@JsonTypeInfo原因：
 * 1.序列化到数据库存储时，增加@class属性
 * 2.反序列化时，根据@class属性，找到对应的类，及创建正确对象
 * </p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface FileClientConfig {
}
