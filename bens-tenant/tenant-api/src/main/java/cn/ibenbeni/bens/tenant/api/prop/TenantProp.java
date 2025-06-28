package cn.ibenbeni.bens.tenant.api.prop;

import cn.ibenbeni.bens.tenant.api.constants.TenantConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * 多租户配置
 *
 * @author: benben
 * @time: 2025/6/27 下午2:10
 */
@Data
@ConfigurationProperties(prefix = "bens.tenant")
public class TenantProp {

    /**
     * 是否开启多租户
     */
    private Boolean enable = TenantConstants.ENABLE_DEFAULT;

    /**
     * 忽略租户的表
     */
    private Set<String> ignoreTables = new HashSet<>();

}
