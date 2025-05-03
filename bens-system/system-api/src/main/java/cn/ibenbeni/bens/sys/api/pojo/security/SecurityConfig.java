package cn.ibenbeni.bens.sys.api.pojo.security;

import lombok.Data;

/**
 * @author benben
 * @date 2025/4/19  下午1:46
 */
@Data
public class SecurityConfig {

    /**
     * 最大密码重试次数
     */
    private Integer maxErrorLoginCount;

    /**
     * 密码策略：最少多久更新一次密码，单位天
     */
    private Integer passwordMinUpdateDays;

    /**
     * 密码历史不可重复次数
     */
    private Integer passwordMinCantRepeatTimes;

    /**
     * 密码策略：口令最小长度
     */
    private Integer minPasswordLength;

    /**
     * 密码策略：最少特殊符号数量
     */
    private Integer passwordMinSpecialSymbolCount;

    /**
     * 密码策略：最少大写字母数量
     */
    private Integer getPasswordMinUpperCaseCount;

    /**
     * 密码策略：最少小写字母数量
     */
    private Integer passwordMinLowerCaseCount;

    /**
     * 密码策略：最少数字符号的数量
     */
    private Integer passwordMinNumberCount;

}
