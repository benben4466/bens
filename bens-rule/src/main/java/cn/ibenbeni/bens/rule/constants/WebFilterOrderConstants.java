package cn.ibenbeni.bens.rule.constants;

/**
 * Web过滤器排序常量
 *
 * @author: benben
 * @time: 2025/6/27 下午4:48
 */
public interface WebFilterOrderConstants {

    /**
     * 权限过滤器
     */
    int PERMISSION_AUTHENTICATION_FILTER = 100;

    /**
     * 租户上下文过滤器
     */
    int TENANT_CONTEXT_FILTER = 120;

    /**
     * 跨域过滤器
     */
    int CORS_FILTER = Integer.MIN_VALUE;

}
