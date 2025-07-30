package cn.ibenbeni.bens.jwt.api.pojo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfig {

    /**
     * JWT密钥
     */
    // TODO JWT密钥可配置
    private String jwtSecret = "123456789abc123456789abc123456789abc";

    /**
     * 过期时间
     */
    private Long expiredSeconds = 3600L * 24;

}
