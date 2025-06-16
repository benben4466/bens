package cn.ibenbeni.bens.validator.api.context;

/**
 * 保持控制器上使用的校验分组
 *
 * @author: benben
 * @time: 2025/6/15 下午4:25
 */
public class RequestGroupContext {

    /**
     * 当前正在使用的校验分组
     */
    private static final ThreadLocal<Class<?>> GROUP_CLASS_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前校验分组
     *
     * @author fengshuonan
     * @since 2020/11/4 14:32
     */
    public static void set(Class<?> groupValue) {
        GROUP_CLASS_HOLDER.set(groupValue);
    }

    /**
     * 获取当前校验分组
     */
    public static Class<?> get() {
        return GROUP_CLASS_HOLDER.get();
    }

    /**
     * 清除当前校验分组缓存
     */
    public static void clear() {
        GROUP_CLASS_HOLDER.remove();
    }

}
