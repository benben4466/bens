package cn.ibenbeni.bens.excel.api.exception.enums;

import cn.ibenbeni.bens.excel.api.constants.ExcelConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * @author: benben
 * @time: 2025/7/6 下午4:21
 */
@Getter
public enum ExcelExceptionEnum implements AbstractExceptionEnum {

    /**
     * 请求状态值为空
     */
    REQUEST_ERROR(ExcelConstants.EXCEL_EXCEPTION_STEP_CODE + "01", "请求错误"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    @Override
    public String getErrorCode() {
        return "";
    }

    @Override
    public String getUserTip() {
        return "";
    }

    ExcelExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
