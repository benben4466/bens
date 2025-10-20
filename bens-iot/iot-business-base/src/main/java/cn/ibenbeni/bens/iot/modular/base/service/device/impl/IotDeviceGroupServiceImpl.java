package cn.ibenbeni.bens.iot.modular.base.service.device.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceGroupDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.device.mysql.IotDeviceGroupMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceGroupSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * IOT设备分组关系-服务实现类
 */
@Slf4j
@Service
public class IotDeviceGroupServiceImpl implements IotDeviceGroupService {

    @Resource
    private IotDeviceGroupMapper deviceGroupMapper;

    @Override
    public void createDeviceGroup(IotDeviceGroupSaveReq saveReq) {
        IotDeviceGroupDO iotDeviceGroup = BeanUtil.toBean(saveReq, IotDeviceGroupDO.class);
        deviceGroupMapper.insert(iotDeviceGroup);
    }

    @Override
    public void deleteDeviceGroupByGroupId(Long groupId) {
        deviceGroupMapper.deleteDeviceGroupByGroupId(groupId);
    }

    @Override
    public void deleteDeviceGroupByDeviceId(Long deviceId) {
        deviceGroupMapper.deleteDeviceGroupByDeviceId(deviceId);
    }

    @Override
    public void deleteDeviceGroup(Long groupId, Long deviceId) {
        deviceGroupMapper.deleteDeviceGroup(groupId, deviceId);
    }

    @Override
    public IotDeviceGroupDO getDeviceGroupByDeviceId(Long deviceId) {
        return deviceGroupMapper.selectByDeviceId(deviceId);
    }

    @Override
    public List<IotDeviceGroupDO> listDeviceGroupByGroupId(Long groupId) {
        return deviceGroupMapper.selectListByGroupId(groupId);
    }

}
