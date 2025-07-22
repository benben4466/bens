package cn.ibenbeni.bens.jwt.api.constants;

/**
 * JWT模块常量
 */
public interface JwtConstants {

    /**
     * JWT模块名称
     */
    String JWT_MODULE_NAME = "bens-jwt";

    /**
     * 异常枚举的步进值
     */
    String JWT_EXCEPTION_STEP_CODE = "06";

    /**
     * JWT默认失效时间，单位秒，默认1天
     */
    Long DEFAULT_JWT_TIMEOUT_SECONDS = 3600L * 24;

}
