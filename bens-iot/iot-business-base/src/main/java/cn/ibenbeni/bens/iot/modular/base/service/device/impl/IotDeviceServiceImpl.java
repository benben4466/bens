package cn.ibenbeni.bens.iot.modular.base.service.device.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.enums.device.IotDeviceStateEnum;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceAuthReqDTO;
import cn.ibenbeni.bens.iot.api.util.IotDeviceAuthUtils;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.mysql.device.IotDeviceMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDevicePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.rule.util.TimestampUtils;
import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
import cn.ibenbeni.bens.tenant.api.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * IOT设备-服务实现类
 */
@Slf4j
@Service
public class IotDeviceServiceImpl implements IotDeviceService {

    @Resource
    private IotDeviceMapper deviceMapper;

    @Lazy
    @Resource
    private IotProductService productService;

    // region 公共方法

    @Override
    public Long createDevice(IotDeviceSaveReq saveReq) {
        // 1.1 校验产品是否存在
        IotProductDO product = productService.getProduct(saveReq.getProductId());
        if (product == null) {
            throw new IotException(IotExceptionEnum.PRODUCT_NOT_EXISTED);
        }
        // 1.2 校验设备名称在同一产品下是否唯一
        validateDeviceNameUniqueByProduct(product.getProductKey(), saveReq.getDeviceName());
        // 1.3 校验设备SN唯一
        validateDeviceSnUnique(saveReq.getDeviceSn(), null);

        IotDeviceDO iotDevice = BeanUtil.toBean(saveReq, IotDeviceDO.class);
        // 初始化设备
        initDevice(product, iotDevice);
        deviceMapper.insert(iotDevice);
        return iotDevice.getDeviceId();
    }

    @Override
    public void deleteDevice(Long deviceId) {
        validateDeviceExists(deviceId);

        deviceMapper.deleteById(deviceId);
    }

    @Override
    public void deleteDevice(Set<Long> deviceIdSet) {
        deviceMapper.deleteByIds(deviceIdSet);
    }

    @Override
    public void updateDevice(IotDeviceSaveReq updateReq) {
        // 不允许更新
        updateReq.setDeviceName(null);
        updateReq.setProductId(null);

        // 1.1 校验设备是否存在
        IotDeviceDO iotDevice = validateDeviceExists(updateReq.getDeviceId());
        // 1.2 校验设备序列号全局唯一
        validateDeviceSnUnique(updateReq.getDeviceSn(), iotDevice.getDeviceId());

        // 更新
        IotDeviceDO updateObj = BeanUtils.toBean(updateReq, IotDeviceDO.class);
        deviceMapper.updateById(updateObj);
    }

    @Override
    public void updateDeviceState(IotDeviceDO device, Integer status) {
        IotDeviceDO updateDO = IotDeviceDO.builder()
                .deviceId(device.getDeviceId())
                .statusFlag(status)
                .build();
        // 设备上线时间为 null 且 设置设备状态=在线时，则设置激活时间
        if (device.getOnlineTime() == null && ObjectUtil.equal(status, IotDeviceStateEnum.ONLINE.getState())) {
            updateDO.setActiveTime(TimestampUtils.curUtcMillis());
        }

        if (ObjectUtil.equal(status, IotDeviceStateEnum.ONLINE.getState())) {
            updateDO.setOnlineTime(TimestampUtils.curUtcMillis());
        } else if (ObjectUtil.equal(status, IotDeviceStateEnum.OFFLINE.getState())) {
            updateDO.setOfflineTime(TimestampUtils.curUtcMillis());
        }
        deviceMapper.updateById(updateDO);
    }

    @Override
    public IotDeviceDO getDevice(Long deviceId) {
        return deviceMapper.selectById(deviceId);
    }

    @TenantIgnore
    @Override
    public IotDeviceDO getDeviceFromCache(Long deviceId) {
        return deviceMapper.selectById(deviceId);
    }

    @TenantIgnore // 忽略租户信息
    @Override
    public IotDeviceDO getDevice(String productKey, String deviceSn) {
        return deviceMapper.selectByProductKeyAndDeviceSn(productKey, deviceSn);
    }

    @Override
    public Long getDeviceCountByProductId(Long productId) {
        return deviceMapper.selectCountByProductId(productId);
    }

    @Override
    public Map<Long, Long> getDeviceCountByProductId(Set<Long> productIdSet) {
        Map<Long, Map<Long, Long>> map = deviceMapper.getDeviceCountByProductId(productIdSet);
        if (CollUtil.isEmpty(map)) {
            return new HashMap<>();
        }

        HashMap<Long, Long> result = new HashMap<>();
        Set<Long> keySet = map.keySet();
        for (Long productId : keySet) {
            Map<Long, Long> objMap = map.get(productId);
            result.put(productId, objMap.get("deviceCount"));
        }

        return result;
    }

    @Override
    public PageResult<IotDeviceDO> pageDevice(IotDevicePageReq pageReq) {
        return deviceMapper.pageDevice(pageReq);
    }

    @Override
    public IotDeviceDO validateDeviceExists(Long deviceId) {
        IotDeviceDO iotDevice = deviceMapper.selectById(deviceId);
        if (iotDevice == null) {
            throw new IotException(IotExceptionEnum.DEVICE_NOT_EXISTED);
        }
        return iotDevice;
    }

    @Override
    public Boolean authDevice(IotDeviceAuthReqDTO authReq) {
        // 1.校验认证参数
        IotDeviceAuthUtils.DeviceInfo deviceInfo = IotDeviceAuthUtils.parseUsername(authReq.getUsername());
        if (deviceInfo == null) {
            log.error("[authDevice][认证失败，username({}) 格式不正确]", authReq.getUsername());
            return false;
        }

        String productKey = deviceInfo.getProductKey();
        String deviceSn = deviceInfo.getDeviceSn();
        // 2.校验设备是否存在
        IotDeviceDO device = getSelf().getDevice(productKey, deviceSn);
        if (device == null) {
            log.warn("[authDevice][设备({}/{}) 不存在]", productKey, deviceSn);
            return false;
        }

        // 3.连接密码校验
        IotDeviceAuthUtils.AuthInfo authInfo = IotDeviceAuthUtils.getAuthInfo(productKey, deviceSn, device.getDeviceSecret());
        if (ObjUtil.notEqual(authInfo.getPassword(), authReq.getPassword())) {
            log.error("[authDevice][设备({}/{}) 密码不正确]", productKey, deviceSn);
            return false;
        }

        return true;
    }

    // endregion

    // region 私有方法

    /**
     * 校验设备名称在同一产品下是否唯一
     *
     * @param productKey 产品Key
     * @param deviceName 设备名称
     */
    private void validateDeviceNameUniqueByProduct(String productKey, String deviceName) {
        TenantUtils.executeIgnore(() -> {
            if (deviceMapper.selectByProductKeyAndDeviceName(productKey, deviceName) != null) {
                throw new IotException(IotExceptionEnum.DEVICE_NAME_EXISTS);
            }
        });
    }

    /**
     * 校验设备SN唯一
     *
     * @param deviceSn        设备SN
     * @param excludeDeviceId 排除的设备ID（用于更新时排除自身）
     */
    private void validateDeviceSnUnique(String deviceSn, Long excludeDeviceId) {
        if (StrUtil.isBlank(deviceSn)) {
            return;
        }
        IotDeviceDO existDevice = deviceMapper.selectDeviceSn(deviceSn);
        if (existDevice != null && ObjectUtil.notEqual(existDevice.getDeviceId(), excludeDeviceId)) {
            throw new IotException(IotExceptionEnum.DEVICE_SN_NOT_EXISTS);
        }
    }

    /**
     * 初始化设备
     *
     * @param iotProduct 产品信息
     * @param iotDevice  设备信息
     */
    private void initDevice(IotProductDO iotProduct, IotDeviceDO iotDevice) {
        iotDevice.setProductId(iotProduct.getProductId());
        iotDevice.setProductKey(iotProduct.getProductKey());
        iotDevice.setDeviceType(iotProduct.getDeviceType());
        // 生成设备密钥
        iotDevice.setDeviceSecret(generateDeviceSecret());
        // 设置设备状态为未激活
        iotDevice.setStatusFlag(IotDeviceStateEnum.INACTIVE.getState());
    }

    /**
     * 生成设备密钥
     */
    private String generateDeviceSecret() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 为了解决 Spring AOP 在类内部调用自己方法不生效问题
     */
    private IotDeviceServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

    // endregion

}
