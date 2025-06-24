package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.exception.enums.http.ServletExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 保存Http请求的上下文，在任何地方快速获取HttpServletRequest和HttpServletResponse
 *
 * @author benben
 * @date 2025/5/20  下午2:16
 */
@Slf4j
public final class HttpServletUtil {

    /**
     * 本机ip地址
     */
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * Nginx代理自定义的IP名称
     */
    private static final String AGENT_SOURCE_IP = "Agent-Source-Ip";

    /**
     * 本机ip地址的ipv6地址
     */
    private static final String LOCAL_REMOTE_HOST = "0:0:0:0:0:0:0:1";

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前请求的Response对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new ServiceException(ServletExceptionEnum.HTTP_CONTEXT_ERROR);
        } else {
            return requestAttributes.getResponse();
        }
    }

    /**
     * 获取客户端IP
     * <p>如果获取不到或者获取到的是ipv6地址，都返回127.0.0.1</p>
     */
    public static String getRequestClientIp(HttpServletRequest request) {
        if (ObjectUtil.isEmpty(request)) {
            return LOCAL_IP;
        }
        String remoteHost = ServletUtil.getClientIP(request, AGENT_SOURCE_IP);
        return LOCAL_REMOTE_HOST.equals(remoteHost) ? LOCAL_IP : remoteHost;
    }

}
