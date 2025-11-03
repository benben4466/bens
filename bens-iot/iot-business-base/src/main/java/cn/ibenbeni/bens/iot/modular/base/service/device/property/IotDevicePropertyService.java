package cn.ibenbeni.bens.iot.modular.base.service.device.property;

import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * IOT-设备属性-服务
 */
public interface IotDevicePropertyService {

    // region 设备属性相关方法

    /**
     * 定义设备属性数据的结构
     *
     * @param productId 产品ID
     */
    void defineDevicePropertyData(Long productId);

    /**
     * 保存设备属性数据
     *
     * @param device        设备
     * @param deviceMessage 设备消息
     */
    void saveDeviceProperty(IotDeviceDO device, IotDeviceMessage deviceMessage);

    // endregion

    /**
     * 获得设备关联的网关服务 serverId
     *
     * @param deviceId 设备ID
     * @return 网关 ServerId
     */
    String getDeviceServerId(Long deviceId);

}
