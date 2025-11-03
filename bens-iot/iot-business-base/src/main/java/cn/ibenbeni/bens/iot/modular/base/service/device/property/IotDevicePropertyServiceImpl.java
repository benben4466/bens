package cn.ibenbeni.bens.iot.modular.base.service.device.property;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.api.core.tdengine.TDengineTableField;
import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotDataSpecsDataTypeEnum;
import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotThingModelTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.redis.device.IotDeviceServerIdRedisDAO;
import cn.ibenbeni.bens.iot.modular.base.mapper.tdengine.device.IotDevicePropertyMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.dataType.ThingModelDateOrTextDataSpecs;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.iot.modular.base.service.thingmodel.IotThingModelService;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.rule.util.JsonUtils;
import cn.ibenbeni.bens.rule.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * IOT-设备属性-服务实现类
 */
@Slf4j
@Service
public class IotDevicePropertyServiceImpl implements IotDevicePropertyService {

    /**
     * 物模型数据类型 与 TDengine 数据类型映射关系
     *
     * @see <a href="https://docs.taosdata.com/reference/taos-sql/datatype/">TDengine 数据类型</a>
     */
    private static final Map<String, String> TYPE_MAPPING = MapUtil.<String, String>builder()
            .put(IotDataSpecsDataTypeEnum.INT.getDataType(), TDengineTableField.TYPE_INT)
            .put(IotDataSpecsDataTypeEnum.FLOAT.getDataType(), TDengineTableField.TYPE_FLOAT)
            .put(IotDataSpecsDataTypeEnum.DOUBLE.getDataType(), TDengineTableField.TYPE_DOUBLE)
            .put(IotDataSpecsDataTypeEnum.ENUM.getDataType(), TDengineTableField.TYPE_VARCHAR)
            .put(IotDataSpecsDataTypeEnum.BOOL.getDataType(), TDengineTableField.TYPE_TINYINT)
            .put(IotDataSpecsDataTypeEnum.TEXT.getDataType(), TDengineTableField.TYPE_VARCHAR)
            .put(IotDataSpecsDataTypeEnum.DATE.getDataType(), TDengineTableField.TYPE_TIMESTAMP)
            .put(IotDataSpecsDataTypeEnum.STRUCT.getDataType(), TDengineTableField.TYPE_VARCHAR)
            .put(IotDataSpecsDataTypeEnum.ARRAY.getDataType(), TDengineTableField.TYPE_VARCHAR)
            .build();

    @Resource
    private IotDevicePropertyMapper devicePropertyMapper;

    @Resource
    private IotDeviceServerIdRedisDAO deviceServerIdRedisDAO;

    @Lazy
    @Resource
    private IotProductService productService;

    @Resource
    private IotThingModelService thingModelService;

    // region 公共方法

    /**
     * 思路：
     * 1.校验产品是否存在
     * 2.获取当前产品的物模型-属性列表（新列表）
     * 3.获取产品属性表的属性字段列表（旧列表）
     * 4.若旧列表为空，则创建表；否则判断旧列表是否一致，不一致则修改表结构
     *
     * @param productId 产品ID
     */
    @Override
    public void defineDevicePropertyData(Long productId) {
        // 获取产品和物模型(属性)
        productService.validateProductExists(productId);
        List<IotThingModelDO> iotThingModels = CollectionUtils.filterList(
                thingModelService.listByProductId(productId),
                thingModel -> IotThingModelTypeEnum.PROPERTY.getType().equals(thingModel.getType())
        );

        // 解析DB中字段
        List<TDengineTableField> oldFields = new ArrayList<>();
        try {
            oldFields.addAll(devicePropertyMapper.getProductPropertySTableFieldList(productId));
        } catch (Exception ex) {
            // 忽略表不存在的异常
            if (!ex.getMessage().contains("Table does not exist")) {
                throw ex;
            }
        }

        // 根据物模型构建表字段列表
        List<TDengineTableField> newFields = buildTableFieldList(iotThingModels);
        if (CollUtil.isEmpty(oldFields)) {
            if (CollUtil.isEmpty(newFields)) {
                log.info("[defineDevicePropertyData][productId({}) 没有需要定义的属性]", productId);
                return;
            }
            devicePropertyMapper.createProductPropertySTable(productId, newFields);
            return;
        }

        // 创建表或修改表结构
        devicePropertyMapper.alterProductPropertySTable(productId, oldFields, newFields);
    }

    @Override
    public void saveDeviceProperty(IotDeviceDO device, IotDeviceMessage deviceMessage) {
        if (!(deviceMessage.getParams() instanceof Map)) {
            log.error("[saveDeviceProperty][消息内容({}) 的 params 类型不正确]", deviceMessage);
            return;
        }

        // 1.根据物模型定义，整理属性数据
        List<IotThingModelDO> thingModels = thingModelService.listByProductId(device.getProductId());
        Map<String, Object> properties = new HashMap<>();
        ((Map<?, ?>) deviceMessage.getParams()).forEach((key, value) -> {
            IotThingModelDO thingModel = CollUtil.findOne(thingModels, item -> item.getIdentifier().equals(key));
            if (thingModel == null || thingModel.getProperty() == null) {
                log.error("[saveDeviceProperty][消息({}) 的属性({}) 不存在]", deviceMessage, key);
                return;
            }

            // 若物模型数据类型是 STRUCT/ARRAY 类型，在TDengine中无对应数据类型，则将数据转为JSON字符串保存
            if (ObjectUtils.equalsAny(thingModel.getProperty().getDataType(),
                    IotDataSpecsDataTypeEnum.STRUCT.getDataType(), IotDataSpecsDataTypeEnum.ARRAY.getDataType())) {
                properties.put((String) key, JsonUtils.toJsonStr(value));
            } else {
                properties.put((String) key, value);
            }
        });
        if (CollUtil.isEmpty(properties)) {
            log.error("[saveDeviceProperty][消息({}) 没有合法的属性]", deviceMessage);
            return;
        }

        // 2.保存设备属性数据
        devicePropertyMapper.insert(device, properties, deviceMessage.getReportTime());
    }

    @Override
    public String getDeviceServerId(Long deviceId) {
        return deviceServerIdRedisDAO.getServerId(deviceId);
    }

    @Override
    public void updateDeviceServerIdAsync(Long deviceId, String serverId) {
        deviceServerIdRedisDAO.updateServerId(deviceId, serverId);
    }

    // endregion

    // region 私有方法

    /**
     * 根据物模型，构建表字段列表
     *
     * @param thingModels 属性物模型
     * @return 表字段列表
     */
    private List<TDengineTableField> buildTableFieldList(List<IotThingModelDO> thingModels) {
        return CollectionUtils.convertList(thingModels, thingModel -> {
            String dataType = thingModel.getProperty().getDataType();
            TDengineTableField field = new TDengineTableField(
                    StrUtil.toUnderlineCase(thingModel.getIdentifier()),
                    TYPE_MAPPING.get(dataType)
            );

            if (Objects.equals(dataType, IotDataSpecsDataTypeEnum.TEXT.getDataType())) {
                field.setLength(((ThingModelDateOrTextDataSpecs) thingModel.getProperty().getDataSpecs()).getLength());
            } else if (ObjectUtils.equalsAny(dataType, IotDataSpecsDataTypeEnum.STRUCT.getDataType(), IotDataSpecsDataTypeEnum.ARRAY.getDataType())) {
                field.setLength(TDengineTableField.LENGTH_VARCHAR);
            }
            return field;
        });
    }

    // endregion

}
