package cn.ibenbeni.bens.iot.api.pojo.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * IOT-设备认证入参
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotDeviceAuthReqDTO {

    /**
     * 客户端ID
     */
    @NotEmpty(message = "客户端ID不能为空")
    private String clientId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

}
