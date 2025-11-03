package cn.ibenbeni.bens.iot.modular.base.mapper.device.tdengine;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.api.core.tdengine.TDengineTableField;
import cn.ibenbeni.bens.iot.api.core.tdengine.annotation.TDengineDS;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备属性-Mapper
 * <p>存储位置: TDengine</p>
 */
@TDengineDS
@InterceptorIgnore(tenantLine = "true") // 避免 SQL 解析，因为 JSqlParser 对 TDengine 的 SQL 解析会报错
public interface IotDevicePropertyMapper {

    /**
     * 创建产品属性表
     *
     * @param productId 产品ID
     * @param fields    属性字段列表
     */
    void createProductPropertySTable(@Param("productId") Long productId, @Param("fields") List<TDengineTableField> fields);

    /**
     * 插入设备属性数据
     *
     * @param device     设备
     * @param properties 属性数据
     * @param reportTime 上报时间
     */
    void insert(@Param("device") IotDeviceDO device, @Param("properties") Map<String, Object> properties, @Param("reportTime") Long reportTime);

    /**
     * 修改产品属性表字段
     *
     * @param productId 产品ID
     * @param oldFields 旧字段列表
     * @param newFields 新字段列表
     */
    default void alterProductPropertySTable(Long productId, List<TDengineTableField> oldFields, List<TDengineTableField> newFields) {
        // 移除物模型外的固定字段
        oldFields.removeIf(field -> StrUtil.equalsAny(field.getField(), TDengineTableField.REPORT_TIME, TDengineTableField.DEVICE_ID));

        // 新增字段
        List<TDengineTableField> addFields = newFields.stream()
                .filter(newField -> oldFields.stream().noneMatch(oldField -> oldField.getField().equals(newField.getField())))
                .collect(Collectors.toList());
        // 删除字段
        List<TDengineTableField> dropFields = oldFields.stream()
                .filter(oldField -> newFields.stream().noneMatch(n -> n.getField().equals(oldField.getField())))
                .collect(Collectors.toList());

        // 变更类型的字段
        List<TDengineTableField> modifyTypeFields = new ArrayList<>();
        // 变更长度的字段
        List<TDengineTableField> modifyLengthFields = new ArrayList<>();
        newFields.forEach(newField -> {
            TDengineTableField oldField = CollUtil.findOne(oldFields, field -> field.getField().equals(newField.getField()));
            if (oldField == null) {
                return;
            }
            // 类型变更
            if (ObjectUtil.notEqual(oldField.getType(), newField.getType())) {
                modifyTypeFields.add(newField);
                return;
            }
            // 长度变更
            if (newField.getLength() != null) {
                if (newField.getLength() > oldField.getLength()) {
                    modifyLengthFields.add(newField);
                } else if (newField.getLength() < oldField.getLength()) {
                    // 特殊: TDengine 长度修改时，只允许变长，所以此时认为是修改类型
                    modifyTypeFields.add(newField);
                }
            }
        });

        // 执行
        addFields.forEach(field -> alterProductPropertySTableAddField(productId, field));
        dropFields.forEach(field -> alterProductPropertySTableDropField(productId, field));
        modifyLengthFields.forEach(field -> alterProductPropertySTableModifyField(productId, field));
        modifyTypeFields.forEach(field -> {
            alterProductPropertySTableDropField(productId, field);
            alterProductPropertySTableAddField(productId, field);
        });
    }

    /**
     * 添加设备属性表字段
     *
     * @param productId 产品ID
     * @param field     字段
     */
    void alterProductPropertySTableAddField(@Param("productId") Long productId, @Param("field") TDengineTableField field);

    /**
     * 删除设备属性表字段
     *
     * @param productId 产品ID
     * @param field     字段
     */
    void alterProductPropertySTableDropField(@Param("productId") Long productId, @Param("field") TDengineTableField field);

    /**
     * 修改设备属性表字段
     *
     * @param productId 产品ID
     * @param field     字段
     */
    void alterProductPropertySTableModifyField(@Param("productId") Long productId, @Param("field") TDengineTableField field);

    /**
     * 获取产品属性字段列表
     *
     * @param productId 产品ID
     */
    List<TDengineTableField> getProductPropertySTableFieldList(@Param("productId") Long productId);

}
