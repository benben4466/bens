package cn.ibenbeni.bens.sys.api.pojo.user;

import lombok.Builder;
import lombok.Data;

/**
 * 用户信息的简单包装
 *
 * @author benben
 * @date 2025/4/19  下午1:29
 */
@Data
@Builder
public class SimpleUserDTO {

    /**
     * 主键
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 头像地址
     */
    private String avatarUrl;

}
