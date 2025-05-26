package cn.ibenbeni.bens.auth.api.constants;

/**
 * @author benben
 * @date 2025/5/3  下午11:06
 */
public interface AuthConstants {

    /**
     * auth模块的名称
     */
    String AUTH_MODULE_NAME = "bens-auth";

    /**
     * 异常枚举的步进值
     */
    String AUTH_EXCEPTION_STEP_CODE = "03";

    /**
     * 登录用户的缓存前缀
     */
    String LOGGED_TOKEN_PREFIX = "LOGGED_TOKEN_";

    /**
     * 获取HTTP请求携带Token的param的名称
     */
    String DEFAULT_AUTH_PARAM_NAME = "token";

    /**
     * 获取HTTP请求携带token的Cookie的名称
     */
    String DEFAULT_AUTH_COOKIE_NAME = "token";

    /**
     * 默认http请求携带token的header名称
     */
    String DEFAULT_AUTH_HEADER_NAME = "Authorization";

}
