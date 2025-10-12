package cn.ibenbeni.bens.iot.modular.base.service.device;

import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceGroupDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceGroupSaveReq;

import java.util.List;

/**
 * IOT设备分组关系-服务
 */
public interface IotDeviceGroupService {

    /**
     * 创建设备分组关系
     */
    void createDeviceGroup(IotDeviceGroupSaveReq saveReq);

    /**
     * 根据设备分组ID，删除设备分组关系
     *
     * @param groupId 设备分组ID
     */
    void deleteDeviceGroupByGroupId(Long groupId);

    /**
     * 根据设备ID，删除设备分组关系
     *
     * @param deviceId 设备ID
     */
    void deleteDeviceGroupByDeviceId(Long deviceId);

    /**
     * 删除设备分组关系
     *
     * @param groupId  设备分组ID
     * @param deviceId 设备ID
     */
    void deleteDeviceGroup(Long groupId, Long deviceId);

    /**
     * 根据设备ID, 获取设备分组关系信息
     *
     * @param deviceId 设备ID
     * @return 设备分组关系信息
     */
    IotDeviceGroupDO getDeviceGroupByDeviceId(Long deviceId);

    /**
     * 根据设备分组ID, 获取设备分组关系列表
     *
     * @param groupId 设备分组ID
     * @return 设备分组ID下所有设备分组关系
     */
    List<IotDeviceGroupDO> listDeviceGroupByGroupId(Long groupId);

}
