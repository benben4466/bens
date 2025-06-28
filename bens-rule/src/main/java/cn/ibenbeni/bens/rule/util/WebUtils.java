package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.util.NumberUtil;
import cn.ibenbeni.bens.rule.constants.RequestHeaderConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Web工具类
 *
 * @author: benben
 * @time: 2025/6/27 下午4:32
 */
public class WebUtils {

    /**
     * 获取当前请求租户ID
     *
     * @param request     请求
     */
    public static Long getTenantId(HttpServletRequest request) {
        String tenantId = request.getHeader(RequestHeaderConstants.TENANT_HEADER);
        return NumberUtil.isNumber(tenantId) ? Long.parseLong(tenantId) : null;
    }

}
