package cn.ibenbeni.bens.iot.modular.base.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.iot.api.IotDeviceCommonApi;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceAuthReqDTO;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceRespDTO;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
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
    private IotProductService productService;

    @Resource
    private IotDeviceService deviceService;

    @Override
    public IotDeviceRespDTO getDeviceFromCache(Long deviceId) {
        return BeanUtil.toBean(deviceService.getDeviceFromCache(deviceId), IotDeviceRespDTO.class);
    }

    @Override
    public IotDeviceRespDTO getDevice(String productKey, String deviceSn) {
        IotProductDO product = productService.getProductFromCache(productKey);
        IotDeviceRespDTO respDTO = BeanUtil.toBean(deviceService.getDevice(productKey, deviceSn), IotDeviceRespDTO.class);
        respDTO.setDataFormat(product.getDataFormat());
        return respDTO;
    }

    @Override
    public Boolean authDevice(IotDeviceAuthReqDTO authReq) {
        return deviceService.authDevice(authReq);
    }

}
