package cn.ibenbeni.bens.sys.api.expander;

/**
 * @author benben
 * @date 2025/5/3  下午7:55
 */
public class SysConfigExpander {

    /**
     * 获取默认密码
     */
    public static String getDefaultPassWord() {
        // TODO 登陆模块完成后补充
        // return ConfigContext.me().getSysConfigValueWithDefault("SYS_DEFAULT_PASSWORD", String.class, SysConstants.DEFAULT_LOGIN_PASSWORD);
        return "123456789";
    }

}
