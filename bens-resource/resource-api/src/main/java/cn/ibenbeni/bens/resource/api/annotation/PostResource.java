package cn.ibenbeni.bens.resource.api.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 资源标识
 * <p>替代{@link org.springframework.web.bind.annotation.PostMapping}注解</p>
 * <p>目的：1.增加权限限制</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.POST)
public @interface PostResource {

    /**
     * 是否需要登录
     * <p>true=需要登陆；false=不需要登陆</p>
     */
    boolean requiredLogin() default true;

    /**
     * 是否需要权限
     * <p>true=需要校验；false=不需要校验</p>
     */
    boolean requiredPermission() default false;

    /**
     * 接口校验的权限编码
     * <p>requiredPermission=true，需要填写</p>
     */
    String requirePermissionCode() default StrUtil.EMPTY;

    /**
     * 请求路径
     * <p>同注解属性{@link RequestMapping#value()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    /**
     * 请求路径
     * <p>同注解属性{@link RequestMapping#path()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

    /**
     * 请求方法
     * <p>同注解属性{@link RequestMapping#method()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    RequestMethod[] method() default RequestMethod.POST;

    /**
     * 请求参数
     * <p>同注解属性{@link RequestMapping#params()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    /**
     * 请求头
     * <p>同注解属性{@link RequestMapping#headers()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    /**
     * 请求的Content-Type
     * <p>同注解属性{@link RequestMapping#consumes()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    /**
     * 响应的Content-Type
     * <p>同注解属性{@link RequestMapping#produces()}</p>
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};

}
