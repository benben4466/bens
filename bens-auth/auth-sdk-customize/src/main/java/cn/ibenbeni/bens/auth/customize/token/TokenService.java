package cn.ibenbeni.bens.auth.customize.token;

import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.jwt.api.exception.JwtException;

/**
 * Token-服务接口
 */
public interface TokenService {

    /**
     * 创建Token
     *
     * @param payload JWT载荷
     * @return Token
     */
    String createAccessToken(DefaultJwtPayload payload);

    /**
     * 校验JWT Token是否正确
     *
     * <p>
     * 错误Token情况：
     * 1.JWT Token过期
     * 2.JWT 自身错误
     * </p>
     *
     * @param token JWT Token
     * @throws JwtException JWT相关错误
     */
    boolean validateAccessToken(String token);

    /**
     * 获取JWT的payload
     *
     * @param token Token
     * @return 默认JWT的payload
     */
    DefaultJwtPayload getDefaultPayload(String token);

}
