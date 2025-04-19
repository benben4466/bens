package cn.ibenbeni.bens.db.api.util;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseExpandFieldEntity;

import java.lang.reflect.Field;

/**
 * 实体字段获取工具类
 *
 * @author benben
 * @date 2025/4/18  下午2:07
 */
public class EntityFieldUtil {

    /**
     * 获取指定对象的类中是否包含了指定字段名
     * <p>
     * 主要用在BaseExpandFieldEntity的子类中获取delFlag字段;
     * 因为直接用getField()或者直接用getDeclaredField()都不能获取到父级的private属性的Field信息
     * </p>
     *
     * @param declareObject 原始对象实例
     * @param fieldName     字段名称
     * @return 字段对象
     * @throws NoSuchFieldException 没有字段异常
     */
    public static Field getDeclaredField(Object declareObject, String fieldName) throws NoSuchFieldException {
        if (ObjectUtil.isEmpty(declareObject)) {
            throw new NoSuchFieldException();
        }

        Class<?> objectClass = declareObject.getClass();

        try {
            // 获取指定类中声明的字段
            return objectClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            // 本类中没有这个字段，再去逐级获取父级类中是否有这个字段
            Class<?> superclass = objectClass.getSuperclass();

            // 如果父级是BaseExpandFieldEntity，则直接获取父父级字段
            if (superclass.equals(BaseExpandFieldEntity.class)) {
                Class<?> baseBusinessEntityClass = superclass.getSuperclass();
                return baseBusinessEntityClass.getDeclaredField(fieldName);
            }

            // 如果父级是BaseBusinessEntity，则直接获取父级的字段
            else if (superclass.equals(BaseBusinessEntity.class)) {
                return superclass.getDeclaredField(fieldName);
            }

            // 其他情况不做判断
            else {
                throw ex;
            }
        }
    }

}
