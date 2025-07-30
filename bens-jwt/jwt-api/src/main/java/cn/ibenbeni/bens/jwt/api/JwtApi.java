package cn.ibenbeni.bens.jwt.api;

import cn.ibenbeni.bens.jwt.api.exception.JwtException;

import java.util.Map;

/**
 * JWT API类
 */
public interface JwtApi {

    /**
     * 获取JWT的Payload
     *
     * @param token Token
     * @return JWT的Payload
     */
    Map<String, Object> getJwtPayloadClaims(String token);

    /**
     * 校验JWT Token是否正确，若有异常抛出JwtException
     * <p>
     * JWT Token不正确情况
     * 1.JWT Token过期
     * 2.JWT 自身错误：
     * </p>
     *
     * @param token Token
     * @throws JwtException JWT异常
     */
    void validateToken(String token) throws JwtException;

    /**
     * 校验JWT Token是否正确，若有异常抛出JwtException
     * <p>
     * JWT Token不正确情况
     * 1.JWT Token过期
     * 2.JWT 自身错误：
     * </p>
     *
     * @param token Token
     * @throws JwtException JWT异常
     */
    void validateTokenWithException(String token) throws JwtException;

    /**
     * 校验JWT Token是否过期
     *
     * @param token Token
     * @return true：过期; false：未过期;
     */
    boolean validateTokenIsExpired(String token);

}
