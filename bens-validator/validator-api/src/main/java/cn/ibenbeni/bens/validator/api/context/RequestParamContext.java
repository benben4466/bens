package cn.ibenbeni.bens.validator.api.context;

import cn.hutool.core.lang.Dict;

/**
 * 临时存储HTTP的请求参数
 *
 * @author: benben
 * @time: 2025/6/16 下午9:00
 */
public class RequestParamContext {

    private static final ThreadLocal<Dict> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 保存请求参数
     */
    public static void set(Dict requestParam) {
        CONTEXT_HOLDER.set(requestParam);
    }

    /**
     * 保存请求参数
     */
    public static void setObject(Object requestParam) {
        if (requestParam == null) {
            return;
        }

        if (requestParam instanceof Dict) {
            CONTEXT_HOLDER.set((Dict) requestParam);
        } else {
            CONTEXT_HOLDER.set(Dict.parse(requestParam));
        }
    }

    /**
     * 获取请求参数
     */
    public static Dict get() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除请求参数
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
