package cn.ibenbeni.bens.iot.modular.base.service.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceAuthReqDTO;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDevicePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceSaveReq;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

/**
 * IOT设备-服务
 */
public interface IotDeviceService {

    /**
     * 创建设备
     *
     * @return 设备ID
     */
    Long createDevice(IotDeviceSaveReq saveReq);

    /**
     * 删除设备
     *
     * @param deviceId 设备ID
     */
    void deleteDevice(Long deviceId);

    /**
     * 批量删除设备
     *
     * @param deviceIdSet 设备ID集合
     */
    void deleteDevice(Set<Long> deviceIdSet);

    /**
     * 更新设备
     *
     * @param updateReq 更新设备入参
     */
    void updateDevice(IotDeviceSaveReq updateReq);

    /**
     * 更新设备状态
     *
     * @param device 设备
     * @param status 状态
     */
    void updateDeviceState(IotDeviceDO device, Integer status);

    /**
     * 获取设备
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    IotDeviceDO getDevice(Long deviceId);

    /**
     * 【缓存】获得设备信息
     * <p>注意：该方法会忽略租户条件。调用时，确认不会出现跨租户数据错误</p>
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    IotDeviceDO getDeviceFromCache(Long deviceId);

    /**
     * 获取设备
     * <p>注意：该方法会忽略租户条件。调用时，确认不会出现跨租户数据错误</p>
     *
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 设备信息
     */
    IotDeviceDO getDevice(String productKey, String deviceSn);

    /**
     * 获取产品下的设备数量
     *
     * @param productId 产品ID
     * @return 设备数量
     */
    Long getDeviceCountByProductId(Long productId);

    /**
     * 批量获取产品下的设备数量
     *
     * @param productIdSet 产品ID集合
     * @return key=产品ID, value=产品下设备数量;
     */
    Map<Long, Long> getDeviceCountByProductId(Set<Long> productIdSet);

    /**
     * 获取设备信息分页列表
     *
     * @param pageReq 分页参数
     * @return 设备信息分页列表
     */
    PageResult<IotDeviceDO> pageDevice(IotDevicePageReq pageReq);

    /**
     * 校验设备是否存在
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    IotDeviceDO validateDeviceExists(Long deviceId);

    /**
     * 设备认证
     *
     * @param authReq 设备认证入参
     * @return 设备认证结果；true=认证成功；false=认证失败；
     */
    Boolean authDevice(@Valid IotDeviceAuthReqDTO authReq);

}
