package cn.ibenbeni.bens.auth.customize.token;

import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.customize.pojo.payload.DefaultJwtPayload;
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
    DefaultJwtPayload validateAccessToken(String token) throws AuthException;

}
