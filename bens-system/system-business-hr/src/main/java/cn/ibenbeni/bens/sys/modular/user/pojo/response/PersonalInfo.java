package cn.ibenbeni.bens.sys.modular.user.pojo.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * 用户的个人信息
 *
 * @author benben
 * @date 2025/4/19  下午1:37
 */
@Data
public class PersonalInfo {

    /**
     * 姓名
     */
    private String realName;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像，存的为文件id
     */
    private Long avatar;

    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别：M-男，F-女
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

}
