package cn.ibenbeni.bens.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户信息
 *
 * @author benben
 * @date 2025/5/3  下午11:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户的token
     */
    private String token;

    /**
     * 当前用户激活的组织机构id（正在以哪个身份访问系统）
     */
    private Long currentOrgId;

    /**
     * 当前用户激活的职务id（正在以哪个身份访问系统）
     */
    private Long currentPositionId;

    /**
     * 当前用户语种的标识，例如：chinese，english
     * <p>这个值是根据字典获取，字典类型编码 languages</p>
     * <p>默认语种是中文</p>
     */
    private String tranLanguageCode = RuleConstants.CHINESE_TRAN_LANGUAGE_CODE;

    /**
     * 登录时候的IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录用户的其他信息
     */
    private Dict otherInfos;

    public LoginUser(Long userId, String account, String token) {
        this.userId = userId;
        this.account = account;
        this.token = token;
    }

}
