package cn.ibenbeni.bens.auth.customize.pojo.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT载荷
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultJwtPayload {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 过期时间戳
     * <p>自动生成，不用填充</p>
     */
    private Long expirationTimestamp;

}
