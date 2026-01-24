package cn.ibenbeni.bens.common.core.serialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * Jackson 序列化工具类
 */
public class JacksonUtils {

    /**
     * 全局单例 ObjectMapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 序列化
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MAPPER.registerModule(new JavaTimeModule());

        // 反序列化
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 忽略未知字段
        MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY); // "a" -> ["a"]
    }

    private JacksonUtils() {
        // 禁止实例化
    }

    // region 反序列化

    /**
     * JSON 字符串转对象（支持多态）
     *
     * @param json  字符串
     * @param clazz 目标类型 Class
     * @param <T>   目标类型
     * @return 目标类型对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException ex) {
            throw new RuntimeException("反序列化失败: " + ex.getMessage(), ex);
        }
    }


    // endregion

}
