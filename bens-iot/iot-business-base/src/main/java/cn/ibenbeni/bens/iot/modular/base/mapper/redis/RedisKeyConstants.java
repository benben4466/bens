package cn.ibenbeni.bens.iot.modular.base.mapper.redis;

/**
 * IOT-Redis Key 枚举类
 */
public interface RedisKeyConstants {

    /**
     * 设备关联的网关 serverID 缓存，采用 HASH 结构
     * <p>
     * 格式：
     * KEY： device_server_id
     * HASH KEY：{deviceId}
     * VALUE： serverID（数据类型：String）
     * </p>
     */
    String DEVICE_SERVER_ID = "iot:device_server_id";

}
