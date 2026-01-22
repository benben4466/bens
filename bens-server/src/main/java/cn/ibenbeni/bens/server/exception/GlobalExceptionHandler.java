package cn.ibenbeni.bens.server.exception;

import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.pojo.response.ErrorResponseData;
import cn.ibenbeni.bens.rule.util.ExceptionUtils;
import cn.ibenbeni.bens.server.constants.BensProjectConstants;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author benben
 * @date 2025/5/25  下午5:47
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理业务异常
     */
    //@ExceptionHandler(ServiceException.class)
    public ErrorResponseData<?> serviceExceptionHandler(ServiceException ex) {
        log.error("业务异常: {}", JSON.toJSONString(ex));
        return renderJson(ex.getErrorCode(), ex.getUserTip(), ex);
    }

    private ErrorResponseData<?> renderJson(String code, String message, Throwable throwable) {
        if (throwable != null) {
            ErrorResponseData<?> errorResponseData = new ErrorResponseData<>(code, message);
            ExceptionUtils.fillErrorResponseData(errorResponseData, throwable, BensProjectConstants.ROOT_PACKAGE_NAME);
            return errorResponseData;
        } else {
            return new ErrorResponseData<>(code, message);
        }
    }

}
