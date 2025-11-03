package cn.ibenbeni.bens.iot.modular.base.mapper.mysql.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDevicePageReq;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * IOT设备-Mapper
 */
public interface IotDeviceMapper extends BaseMapperX<IotDeviceDO> {

    default IotDeviceDO selectDeviceSn(String deviceSn) {
        return selectOne(IotDeviceDO::getDeviceSn, deviceSn);
    }

    default IotDeviceDO selectByProductKeyAndDeviceName(String productKey, String deviceName) {
        return selectOne(IotDeviceDO::getProductKey, productKey, IotDeviceDO::getDeviceName, deviceName);
    }

    default IotDeviceDO selectByProductKeyAndDeviceSn(String productKey, String deviceSn) {
        return selectOne(IotDeviceDO::getProductKey, productKey, IotDeviceDO::getDeviceSn, deviceSn);
    }

    default long selectCountByProductId(Long productId) {
        return selectCount(IotDeviceDO::getProductId, productId);
    }

    /**
     * 批量获取产品下的设备数量
     *
     * @param productIdSet 产品ID集合
     * @return key=产品ID, value=产品下设备数量;
     */
    @MapKey("productId") // 让 MyBatis 知道谁作为 Key
    Map<Long, Map<Long, Long>> getDeviceCountByProductId(@Param("productIdSet") Set<Long> productIdSet);

    default PageResult<IotDeviceDO> pageDevice(IotDevicePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<IotDeviceDO>()
                .likeIfPresent(IotDeviceDO::getDeviceName, pageReq.getDeviceName())
                .likeIfPresent(IotDeviceDO::getDeviceSn, pageReq.getDeviceSn())
                .likeIfPresent(IotDeviceDO::getProductKey, pageReq.getProductKey())
                .eqIfPresent(IotDeviceDO::getDeviceType, pageReq.getDeviceType())
                .eqIfPresent(IotDeviceDO::getStatusFlag, pageReq.getStatusFlag())
                .orderByDesc(IotDeviceDO::getDeviceId)
        );
    }

}
