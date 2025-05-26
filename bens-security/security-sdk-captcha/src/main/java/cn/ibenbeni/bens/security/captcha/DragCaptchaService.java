package cn.ibenbeni.bens.security.captcha;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.security.api.DragCaptchaApi;
import cn.ibenbeni.bens.security.api.exception.SecurityException;
import cn.ibenbeni.bens.security.api.exception.enums.SecurityExceptionEnum;
import lombok.AllArgsConstructor;

/**
 * 拖拽验证码实现
 *
 * @author benben
 * @date 2025/5/20  下午4:25
 */
@AllArgsConstructor
public class DragCaptchaService implements DragCaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    @Override
    public boolean validateCaptcha(String verKey, Integer verScope) {
        if (StrUtil.isEmpty(verKey)) {
            return false;
        }
        if (verScope == null) {
            return false;
        }

        // 获取缓存中正确的locationX的值
        String locationXString = cacheOperatorApi.get(verKey);
        if (StrUtil.isEmpty(locationXString)) {
            throw new SecurityException(SecurityExceptionEnum.CAPTCHA_INVALID_ERROR);
        }

        // 构造误差范围
        Integer locationX = Convert.toInt(locationXString);
        int beginScope = locationX - 6;
        int endScope = locationX + 6;

        // 每次验证不管成功和失败都剔除掉key
        cacheOperatorApi.remove(verKey);

        // 验证缓存中的范围值
        return verScope >= beginScope && verScope <= endScope;
    }

}
