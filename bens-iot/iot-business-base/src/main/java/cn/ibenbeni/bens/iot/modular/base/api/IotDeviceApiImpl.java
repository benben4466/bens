package cn.ibenbeni.bens.iot.modular.base.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.iot.api.IotDeviceCommonApi;
import cn.ibenbeni.bens.iot.api.pojo.dto.IotDeviceRespDTO;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * IOT-设备信息-通用接口实现类
 */
@Slf4j
@Service
public class IotDeviceApiImpl implements IotDeviceCommonApi {

    @Resource
    private IotDeviceService deviceService;

    @Override
    public IotDeviceRespDTO getDevice(Long deviceId) {
        return BeanUtil.toBean(deviceService.getDevice(deviceId), IotDeviceRespDTO.class);
    }

    @Override
    public IotDeviceRespDTO getDevice(String productKey, String deviceSn) {
        return BeanUtil.toBean(deviceService.getDevice(productKey, deviceSn), IotDeviceRespDTO.class);
    }

}
