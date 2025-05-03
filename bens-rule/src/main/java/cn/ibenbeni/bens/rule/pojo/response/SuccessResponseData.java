package cn.ibenbeni.bens.rule.pojo.response;

import cn.ibenbeni.bens.rule.constants.RuleConstants;

/**
 * 响应成功的封装类
 *
 * @author benben
 * @date 2025/5/3  下午7:29
 */
public class SuccessResponseData<T> extends ResponseData<T> {

    public SuccessResponseData() {
        super(Boolean.TRUE, RuleConstants.SUCCESS_CODE, RuleConstants.SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(T object) {
        super(Boolean.TRUE, RuleConstants.SUCCESS_CODE, RuleConstants.SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(String code, String message, T object) {
        super(Boolean.TRUE, code, message, object);
    }

}
