package cn.ibenbeni.bens.iot.modular.base.mapper.redis.device;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.iot.modular.base.mapper.redis.RedisKeyConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 设备关联的网关 serverID-Redis DAO
 */
@Repository
public class IotDeviceServerIdRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 更新设备关联的网关 serverID
     *
     * @param deviceId 设备ID
     * @param serverId 网关 serverID
     */
    public void updateServerId(Long deviceId, String serverId) {
        stringRedisTemplate.opsForHash().put(RedisKeyConstants.DEVICE_SERVER_ID, String.valueOf(deviceId), serverId);
    }

    /**
     * 获取设备关联的网关 serverID
     *
     * @param deviceId 设备ID
     */
    public String getServerId(Long deviceId) {
         Object value = stringRedisTemplate.opsForHash().get(RedisKeyConstants.DEVICE_SERVER_ID, String.valueOf(deviceId));
         return ObjectUtil.isNotEmpty(value) ? (String) value : null;
    }

}
