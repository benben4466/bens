package cn.ibenbeni.bens.iot.api;

import cn.ibenbeni.bens.iot.api.pojo.dto.IotDeviceRespDTO;

/**
 * IOT-设备信息-通用接口
 */
public interface IotDeviceCommonApi {

    /**
     * 获取设备信息
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    IotDeviceRespDTO getDevice(Long deviceId);

    /**
     * 获取设备信息
     *
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 设备信息
     */
    IotDeviceRespDTO getDevice(String productKey, String deviceSn);

}
