package cn.ibenbeni.bens.iot.modular.base.service.device.property;

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

    // endregion

}
