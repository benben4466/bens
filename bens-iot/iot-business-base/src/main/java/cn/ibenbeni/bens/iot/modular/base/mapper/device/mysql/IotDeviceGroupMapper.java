package cn.ibenbeni.bens.iot.modular.base.mapper.device.mysql;

import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceGroupDO;

import java.util.List;

/**
 * IOT设备分组关系-Mapper
 */
public interface IotDeviceGroupMapper extends BaseMapperX<IotDeviceGroupDO> {

    default void deleteDeviceGroupByGroupId(Long groupId){
        delete(new LambdaQueryWrapperX<IotDeviceGroupDO>()
                .eq(IotDeviceGroupDO::getDeviceGroupId, groupId)
        );
    }

    default void deleteDeviceGroupByDeviceId(Long deviceId) {
        delete(new LambdaQueryWrapperX<IotDeviceGroupDO>()
                .eq(IotDeviceGroupDO::getDeviceId, deviceId)
        );
    }

    default void deleteDeviceGroup(Long groupId, Long deviceId) {
        delete(new LambdaQueryWrapperX<IotDeviceGroupDO>()
                .eq(IotDeviceGroupDO::getDeviceId, deviceId)
                .eq(IotDeviceGroupDO::getDeviceGroupId, groupId)
        );
    }

    default IotDeviceGroupDO selectByDeviceId(Long deviceId) {
        return selectOne(new LambdaQueryWrapperX<IotDeviceGroupDO>()
                .eq(IotDeviceGroupDO::getDeviceId, deviceId)
        );
    }

    default List<IotDeviceGroupDO> selectListByGroupId(Long groupId) {
        return selectList(new LambdaQueryWrapperX<IotDeviceGroupDO>()
                .eq(IotDeviceGroupDO::getDeviceGroupId, groupId)
        );
    }

}
