package cn.ibenbeni.bens.rule.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请求失败的结果包装类
 *
 * @author benben
 * @date 2025/5/3  下午7:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorResponseData<T> extends ResponseData<T> {

    /**
     * 异常的具体类名称
     */
    private String exceptionClazz;

    /**
     * 异常的提示信息
     */
    private String exceptionTip;

    /**
     * 跟项目有关的具体异常位置
     * <p>一般是堆栈中第一个出现项目包名的地方</p>
     */
    private String exceptionPlace;

    public ErrorResponseData(String code, String message) {
        super(Boolean.FALSE, code, message, null);
    }

    public ErrorResponseData(String code, String message, T object) {
        super(Boolean.FALSE, code, message, object);
    }

}
