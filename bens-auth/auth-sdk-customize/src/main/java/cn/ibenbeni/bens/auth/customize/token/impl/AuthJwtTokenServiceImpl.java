package cn.ibenbeni.bens.auth.customize.token.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.ibenbeni.bens.auth.api.expander.AuthConfigExpander;
import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.auth.customize.token.TokenService;
import cn.ibenbeni.bens.jwt.api.JwtApi;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * Token-服务实现类
 */
@Service
public class AuthJwtTokenServiceImpl implements TokenService {

    @Resource
    private JwtApi jwtApi;

    @Override
    public String createAccessToken(DefaultJwtPayload payload) {
        // 计算过期时间
        DateTime expirationDate = DateUtil.offsetSecond(new Date(), Convert.toInt(AuthConfigExpander.getSessionExpiredSeconds()));

        // 设置过期时间
        payload.setExpirationTimestamp(expirationDate.getTime());

        // 获取符合HMAC签名算法的SecretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(AuthConfigExpander.getAuthJwtSecret().getBytes());

        return Jwts.builder()
                .subject(payload.getUserId().toString()) // 设置JWT主题(sub)，通常是用户唯一标识
                .claims(BeanUtil.beanToMap(payload))     // 设置JWT负载部分
                .issuedAt(new Date())                    // 设置JWT签发时间
                .expiration(expirationDate)              // 设置JWT过期时间
                .signWith(secretKey, Jwts.SIG.HS256)     // 设置JWT签名密钥及签名算法
                .compact();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            jwtApi.validateToken(token);
            jwtApi.getJwtPayloadClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public DefaultJwtPayload getDefaultPayload(String token) {
        Map<String, Object> jwtPayload = jwtApi.getJwtPayloadClaims(token);
        return BeanUtil.toBeanIgnoreError(jwtPayload, DefaultJwtPayload.class);
    }

}
