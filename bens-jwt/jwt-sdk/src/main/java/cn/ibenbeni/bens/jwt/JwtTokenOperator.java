package cn.ibenbeni.bens.jwt;

import cn.ibenbeni.bens.jwt.api.JwtApi;
import cn.ibenbeni.bens.jwt.api.exception.JwtException;
import cn.ibenbeni.bens.jwt.api.exception.enums.JwtExceptionEnum;
import cn.ibenbeni.bens.jwt.api.pojo.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT操作类
 */
public class JwtTokenOperator implements JwtApi {

    private JwtConfig jwtConfig;

    public JwtTokenOperator(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Claims getJwtPayloadClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes());
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public void validateToken(String token) throws JwtException {
        // 判断JWT自身是否正确
        try {
            getJwtPayloadClaims(token);
        } catch (io.jsonwebtoken.JwtException ex) {
            throw new JwtException(JwtExceptionEnum.JWT_PARSE_ERROR);
        }

        // 判断JWT Token是否过期
        boolean tokenIsExpired = validateTokenIsExpired(token);
        if (tokenIsExpired) {
            throw new JwtException(JwtExceptionEnum.JWT_EXPIRED_ERROR);
        }
    }

    @Override
    public void validateTokenWithException(String token) throws JwtException {
        // 判断JWT是否过期
        boolean tokenIsExpired = validateTokenIsExpired(token);
        if (tokenIsExpired) {
            throw new JwtException(JwtExceptionEnum.JWT_EXPIRED_ERROR);
        }

        // 判断JWT自身是否正确
        try {
            getJwtPayloadClaims(token);
        } catch (io.jsonwebtoken.JwtException ex) {
            throw new JwtException(JwtExceptionEnum.JWT_PARSE_ERROR);
        }
    }

    /**
     * 校验Token是否过期
     */
    @Override
    public boolean validateTokenIsExpired(String token) {
        try {
            Claims claims = getJwtPayloadClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

}
