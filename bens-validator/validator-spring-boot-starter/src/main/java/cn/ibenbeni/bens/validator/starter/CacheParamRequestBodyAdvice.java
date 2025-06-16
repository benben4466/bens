package cn.ibenbeni.bens.validator.starter;

import cn.ibenbeni.bens.validator.api.context.RequestParamContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 请求参数增强，缓存@RequestBody的请求参数
 * <p>RequestBodyAdvice解释：
 * 用于在请求体被处理之前执行自定义逻辑。针对带有@RequestBody注解的参数，允许在请求体被反序列化为Java对象之前对其进行拦截和修改。
 * </p>
 *
 * @author: benben
 * @time: 2025/6/16 下午10:00
 */
@Slf4j
@ControllerAdvice
public class CacheParamRequestBodyAdvice implements RequestBodyAdvice {

    /**
     * 判断是否需要对当前请求体进行处理
     *
     * @param methodParameter 当前方法参数信息
     * @param targetType      目标类型的 Type，通常是方法参数的类型
     * @param converterType   将被用来反序列化请求体的 HttpMessageConverter 的类型，若请求体是 JSON，则该类型为MappingJackson2HttpMessageConverter。
     * @return true=则会调用 beforeBodyRead、afterBodyRead 和 handleEmptyBody 方法；如果返回 false，则不会调用这些方法。
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在请求体被读取之前执行的逻辑
     * <p>应用场景：可以用于对请求体进行预处理，例如解密、解码等。</p>
     *
     * @param inputMessage  包含请求的输入流和HTTP头信息
     * @param parameter     同上
     * @param targetType    同上
     * @param converterType 同上
     * @return 新的HttpInputMessage对象，若没有修改，则直接返回
     * @throws IOException IO异常
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * 在请求体被读取并转换为Java对象之后执行的逻辑
     * <p>应用场景：用于对转换后的对象进行进一步处理，例如验证、修改字段值等。</p>
     *
     * @param body          请求体已经被反序列化后的对象
     * @param inputMessage  同上
     * @param parameter     同上
     * @param targetType    同上
     * @param converterType 同上
     * @return 返回处理后的对象，若没有修改，则直接返回
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 临时缓存一下@RequestBody注解上的参数
        RequestParamContext.setObject(body);
        return body;
    }

    /**
     * 在请求体为空时执行的逻辑
     * <p>应用场景：用于处理空请求体的情况，例如提供默认值。</p>
     *
     * @param body          表示请求体为空时的默认值，如果请求体为空，body 可能是 null 或者某个默认值。
     * @param inputMessage  同上
     * @param parameter     同上
     * @param targetType    同上
     * @param converterType 同上
     * @return 返回处理后的对象。如果请求体为空，你可以提供一个默认值；如果没有提供默认值，返回null。
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
