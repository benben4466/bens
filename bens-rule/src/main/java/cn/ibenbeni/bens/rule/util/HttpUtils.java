package cn.ibenbeni.bens.rule.util;

import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * HTTP工具类
 *
 * @author: benben
 * @time: 2025/7/6 下午4:38
 */
public class HttpUtils {

    /**
     * 编码URL参数
     *
     * @param value 参数
     * @return 编码后的参数
     */
    @SneakyThrows
    public static String encodeUtf8(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
    }

}
