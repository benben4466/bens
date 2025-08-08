package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.pojo.response.ErrorResponseData;

/**
 * 异常处理类
 */
public class ExceptionUtils {

    /**
     * 填充错误响应数据
     *
     * @param errorResponseData 错误响应数据对象
     * @param throwable         错误
     * @param projectPackage    项目包名
     */
    public static void fillErrorResponseData(ErrorResponseData<?> errorResponseData, Throwable throwable, String projectPackage) {
        if (errorResponseData == null || throwable == null) {
            return;
        }

        // 填充异常类信息
        errorResponseData.setExceptionClazz(throwable.getClass().getSimpleName());
        // 填充异常提示信息
        errorResponseData.setExceptionTip(throwable.getMessage());
        // 填充第一行项目包路径的堆栈
        errorResponseData.setExceptionPlace(getFirstStackTraceByPackageName(throwable, projectPackage));
    }

    /**
     * 获取第一条包含包名的堆栈信息
     * <p>若包名为空，则直接返回第一条</p>
     *
     * @param throwable   Throwable
     * @param packageName 包名
     * @return 堆栈信息
     */
    public static String getFirstStackTraceByPackageName(Throwable throwable, String packageName) {
        if (throwable == null) {
            return "";
        }

        // 获取堆栈信息
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        // 默认返回第一条堆栈信息
        String stackTraceElementString = stackTrace[0].toString();
        // 包名没传就返第一条堆栈信息
        if (StrUtil.isNotBlank(packageName)) {
            return stackTraceElementString;
        }

        // 找到项目包名开头的第一条异常信息
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (stackTraceElement.toString().contains(packageName)) {
                stackTraceElementString = stackTraceElement.toString();
                break;
            }
        }

        return stackTraceElementString;
    }

}
