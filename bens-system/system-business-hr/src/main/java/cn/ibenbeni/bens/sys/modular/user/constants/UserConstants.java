package cn.ibenbeni.bens.sys.modular.user.constants;

import java.math.BigDecimal;

/**
 * 用户相关的常量
 *
 * @author: benben
 * @time: 2025/6/2 下午3:55
 */
public interface UserConstants {

    /**
     * 缓存前缀：用户绑定的角色
     */
    String USER_ROLE_CACHE_PREFIX = "SYS:USER_ROLE:";

    /**
     * 修改用户绑定角色的事件
     */
    String UPDATE_USER_ROLE_EVENT = "UPDATE_USER_ROLE_EVENT";

    /**
     * 默认用户排序
     */
    BigDecimal DEFAULT_USER_SORT = BigDecimal.valueOf(99);

}
