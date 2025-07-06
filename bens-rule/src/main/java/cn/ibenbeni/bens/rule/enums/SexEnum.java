package cn.ibenbeni.bens.rule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author: benben
 * @time: 2025/7/2 下午4:38
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

    MALE("M", "男"),
    FEMALE("F", "女"),
    UNKNOWN("U", "未知"),

    ;

    private String sexCode;

    private String sexName;

}
