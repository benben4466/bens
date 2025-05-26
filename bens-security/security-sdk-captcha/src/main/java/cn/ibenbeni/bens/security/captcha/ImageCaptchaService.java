package cn.ibenbeni.bens.security.captcha;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.security.api.ImageCaptchaApi;
import lombok.AllArgsConstructor;

/**
 * 图形验证码实现
 *
 * @author benben
 * @date 2025/5/20  下午4:06
 */
@AllArgsConstructor
public class ImageCaptchaService implements ImageCaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    @Override
    public boolean validateCaptcha(String verKey, String verCode) {
        if (StrUtil.isAllEmpty(verKey, verCode)) {
            return false;
        }

        // 校验缓存中验证码
        if (!verCode.trim().toLowerCase().equals(cacheOperatorApi.get(verKey))) {
            return false;
        }

        // 删除缓存中验证码
        cacheOperatorApi.remove(verKey);
        return true;
    }

}
