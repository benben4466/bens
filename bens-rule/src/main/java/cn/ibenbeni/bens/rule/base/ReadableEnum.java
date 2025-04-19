package cn.ibenbeni.bens.rule.base;

/**
 * 可读性枚举的规范，必须包含一个key和一个value
 * <p>
 * key一般是编码，id，具有标识性的类型
 * value一般是String类型，是一串文字
 * </p>
 *
 * @author benben
 */
public interface ReadableEnum<T> {

    /**
     * 获取枚举中具有标识性的key或者id
     * <p>例如：状态枚举中的装填值，1 或 2</p>
     *
     * @return 返回枚举具有标示性的key或id
     */
    Object getKey();

    /**
     * 获取枚举中具有可读性的value值
     * <p>例如：状态枚举中的状态名称，"启用" 或 "禁用"</p>
     *
     * @return 返回枚举具有可读性的value值
     */
    Object getName();

    /**
     * 将原始值转化为具体枚举对象
     *
     * @param originValue 原始值
     * @return T 具体枚举
     */
    T parseToEnum(String originValue);

}
