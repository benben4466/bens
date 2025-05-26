package cn.ibenbeni.bens.auth.api.expander;

/**
 * 权限相关配置快速获取
 *
 * @author benben
 * @date 2025/5/25  下午2:15
 */
public class AuthConfigExpander {

    /**
     * 获取session过期时间，默认3600秒
     * <p>在这个时段内不操作，会将用户踢下线，从新登陆</p>
     * <p>如果开启了记住我功能，在session过期后会从新创建session</p>
     */
    public static Long getSessionExpiredSeconds() {
        // TODO 待完善
        return 3600L;
    }

}
