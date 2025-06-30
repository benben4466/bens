package cn.ibenbeni.bens.tenant.web;

import cn.ibenbeni.bens.rule.util.WebUtils;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 多租户上下文Web过滤器
 * <p>作用：将请求头中租户字段的值解析出来，添加到{@link cn.ibenbeni.bens.tenant.api.context.TenantContextHolder}中</p>
 *
 * @author: benben
 * @time: 2025/6/27 下午4:26
 */
@RequiredArgsConstructor
public class TenantContextWebFilter extends OncePerRequestFilter { // TenantMetaObjectHandler

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 设置租户ID
        Long tenantId = WebUtils.getTenantId(request);
        if (tenantId != null) {
            TenantContextHolder.setTenantId(tenantId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }

}
