package cn.ibenbeni.bens.db.mp.fieldfill;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.constants.DbFieldConstants;
import cn.ibenbeni.bens.db.api.util.EntityFieldUtil;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 字段自动填充工具
 * <p>在mybatis-plus执行更新和新增操作时候，会对指定字段进行自动填充，例如 create_time 等字段</p>
 *
 * @author benben
 * @date 2025/4/18  下午12:42
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            // 填充createTime（BaseEntity）
            setValue(metaObject, DbFieldConstants.CREATE_TIME, new Date());

            // TODO 填充createUser（BaseEntity）-> 登录模块补充后填写
            setValue(metaObject, DbFieldConstants.CREATE_USER, 1L);
            // 设置删除标记；默认N-未删除
            setDelFlagDefaultValue(metaObject);
            // 设置状态字段 默认1-启用
            setStatusDefaultValue(metaObject);
        } catch (ReflectionException ex) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            // 设置updateUser（BaseEntity)
            if (metaObject.hasSetter(DbFieldConstants.UPDATE_USER)) {
                metaObject.setValue(DbFieldConstants.UPDATE_USER, this.getUserUniqueId());
            }
            // 设置updateTime（BaseEntity)
            if (metaObject.hasSetter(DbFieldConstants.UPDATE_TIME)) {
                metaObject.setValue(DbFieldConstants.UPDATE_TIME, new Date());
            }
        } catch (Exception ex) {
            log.warn("CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }
    }

    /**
     * @param metaObject 对象元数据
     * @param fieldName  待填充字段名称
     * @param value      待填充字段值
     */
    protected void setValue(MetaObject metaObject, String fieldName, Object value) {
        // 通过字段名称动态地获取对象中字段的值；若字段不存在或值为null，则返回null
        Object originalAttr = getFieldValByName(fieldName, metaObject);
        if (ObjectUtil.isEmpty(originalAttr)) {
            // 根据字段名称填充值；若无该字段，则会忽略
            setFieldValByName(fieldName, value, metaObject);
        }
    }

    /**
     * 设置属性，针对逻辑删除字段
     */
    protected void setDelFlagDefaultValue(MetaObject metaObject) {
        Object originalAttr = getFieldValByName(DbFieldConstants.DEL_FLAG, metaObject);
        if (ObjectUtil.isNotEmpty(originalAttr)) {
            return;
        }
        // 获取原始对象实例
        Object originalObject = metaObject.getOriginalObject();
        try {
            // 获取delFlag字段的类型，如果是枚举类型，则设置枚举
            Field declaredField = EntityFieldUtil.getDeclaredField(originalObject, DbFieldConstants.DEL_FLAG);
            if (ClassUtil.isEnum(declaredField.getType())) {
                setFieldValByName(DbFieldConstants.DEL_FLAG, YesOrNotEnum.N, metaObject);
            } else {
                setFieldValByName(DbFieldConstants.DEL_FLAG, YesOrNotEnum.N.getCode(), metaObject);
            }
        } catch (Exception ignore) {
            // 没有字段，忽略
        }
    }

    /**
     * 设置属性，针对状态字段
     */
    private void setStatusDefaultValue(MetaObject metaObject) {
        Object originalAttr = getFieldValByName(DbFieldConstants.DEL_FLAG, metaObject);
        if (ObjectUtil.isNotEmpty(originalAttr)) {
            return;
        }
        // 获取原始对象实例
        Object originalObject = metaObject.getOriginalObject();
        try {
            // 获取statusFlag字段的类型，如果是枚举类型，则设置枚举
            Field declaredField = EntityFieldUtil.getDeclaredField(originalObject, DbFieldConstants.DEL_FLAG);
            if (ClassUtil.isEnum(declaredField.getType())) {
                setFieldValByName(DbFieldConstants.STATUS_FLAG, StatusEnum.ENABLE, metaObject);
            } else {
                setFieldValByName(DbFieldConstants.STATUS_FLAG, StatusEnum.ENABLE.getCode(), metaObject);
            }
        } catch (Exception ignore) {
            // 没有字段，忽略
        }
    }

    /**
     * 获取用户唯一id
     */
    protected Long getUserUniqueId() {
        try {
            // TODO 登录模块补充后填写
            return 1L;
        } catch (Exception ex) {
            //如果获取不到就返回-1
            return -1L;
        }
    }

}
