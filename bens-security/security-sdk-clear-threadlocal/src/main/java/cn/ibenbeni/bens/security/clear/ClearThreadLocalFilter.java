package cn.ibenbeni.bens.security.clear;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.threadlocal.RemoveThreadLocalApi;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * 清空程序中的ThreadLocal
 *
 * <p>ProjectSecurityAutoConfiguration过滤器在Starter模块中注册</p>
 *
 * @author benben
 * @date 2025/5/20  下午10:31
 */
@Slf4j
public class ClearThreadLocalFilter implements Filter {

    public static final String NAME = "ClearThreadLocalFilter";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            try {
                // 获取到所有线程本地变量Bean
                Map<String, RemoveThreadLocalApi> beansOfType = SpringUtil.getBeansOfType(RemoveThreadLocalApi.class);
                if (beansOfType != null) {
                    for (Map.Entry<String, RemoveThreadLocalApi> entry : beansOfType.entrySet()) {
                        entry.getValue().removeThreadLocalAction();
                    }
                }
            } catch (Exception ex) {
                log.error("{}清空threadLocal失败！", NAME, ex);
            }
        }
    }

}
