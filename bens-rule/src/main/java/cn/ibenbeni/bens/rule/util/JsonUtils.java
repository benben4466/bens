package cn.ibenbeni.bens.rule.util;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * Json工具类
 */
@Slf4j
public class JsonUtils {

    /**
     * 对象转 JSON 字符串
     *
     * @param obj 对象
     * @return JSON 字符串
     */
    public static String toJsonStr(Object obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue);
    }

    public static <T> T parseObject(byte[] text, Type type) {
        if (ArrayUtil.isEmpty(text)) {
            return null;
        }

        try {
            return JSON.parseObject(text, type);
        } catch (Exception ex) {
            log.error("json parse err,json:{}", text, ex);
            throw new RuntimeException(ex);
        }
    }

}
