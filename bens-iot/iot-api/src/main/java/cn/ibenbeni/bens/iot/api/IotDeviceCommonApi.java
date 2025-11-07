package cn.ibenbeni.bens.iot.api;

import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceAuthReqDTO;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceRespDTO;

/**
 * IOT-设备信息-通用接口
 */
public interface IotDeviceCommonApi {

    /**
     * 获取设备信息
     * <p>注意：该方法会忽略租户条件。调用时，确认不会出现跨租户数据错误</p>
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    IotDeviceRespDTO getDeviceFromCache(Long deviceId);

    /**
     * 获取设备信息
     *
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 设备信息
     */
    IotDeviceRespDTO getDevice(String productKey, String deviceSn);

    /**
     * 设备认证
     *
     * @param authReq 设备认证入参
     * @return 设备认证结果；true=认证成功；false=认证失败；
     */
    Boolean authDevice(IotDeviceAuthReqDTO authReq);

}
