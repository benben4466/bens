package cn.ibenbeni.bens.event.api.exception.enums;

import cn.ibenbeni.bens.event.api.constants.EventConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 业务事件异常枚举
 *
 * @author: benben
 * @time: 2025/6/12 下午9:54
 */
@Getter
public enum EventExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询不到对应业务事件
     */
    CANT_FIND_EVENT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + EventConstants.EVENT_EXCEPTION_STEP_CODE + "01", "查询不到对应业务事件，具体信息：{}"),

    /**
     * 事件调用，方法调用失败
     */
    ERROR_INVOKE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + EventConstants.EVENT_EXCEPTION_STEP_CODE + "02", "事件调用，方法调用失败"),
    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    EventExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
