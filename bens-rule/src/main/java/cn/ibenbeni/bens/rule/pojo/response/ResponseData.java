package cn.ibenbeni.bens.rule.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP响应结果封装
 *
 * @author benben
 * @date 2025/5/3  下午7:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private T data;

}
