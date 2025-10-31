package cn.ibenbeni.bens.iot.api.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IoT 设备【认证】的工具类，参考阿里云
 *
 * @see <a href="https://help.aliyun.com/zh/iot/user-guide/how-do-i-obtain-mqtt-parameters-for-authentication">如何计算 MQTT 签名参数</a>
 */
public class IotDeviceAuthUtils {

    /**
     * 连接密码模板
     */
    private final static String PASSWORD_TEMPLATE = "clientId{}&productKey{}&deviceSn{}&deviceSecret{}";

    /**
     * 认证信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthInfo {

        /**
         * 客户端 ID
         */
        private String clientId;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

    }

    /**
     * 设备信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceInfo {

        private String productKey;

        private String deviceSn;

    }

    /**
     * 解析用户名
     *
     * @param username 用户名
     * @return 设备信息
     */
    public static DeviceInfo parseUsername(String username) {
        String[] usernameParts = username.split("&");
        if (usernameParts.length != 2) {
            return null;
        }
        return DeviceInfo.builder()
                .productKey(usernameParts[0])
                .deviceSn(usernameParts[1])
                .build();
    }

    /**
     * 获取设备认证信息
     *
     * @param productKey   产品Key
     * @param deviceSn     设备SN
     * @param deviceSecret 设备密钥
     * @return 设备认证信息
     */
    public static AuthInfo getAuthInfo(String productKey, String deviceSn, String deviceSecret) {
        String clientId = deviceSn;
        String username = buildUsername(productKey, deviceSn);
        String password = buildPassword(clientId, productKey, deviceSn, deviceSecret);
        return new AuthInfo(clientId, username, password);
    }

    /**
     * 构建连接用户名
     *
     * @param productKey 产品Key
     * @param deviceName 设备名称
     * @return 连接用户名
     */
    private static String buildUsername(String productKey, String deviceName) {
        return String.format("%s&%s", deviceName, productKey);
    }

    /**
     * 构建连接密码
     *
     * @param clientId     客户端ID
     * @param productKey   产品Key
     * @param deviceSn     设备SN
     * @param deviceSecret 设备密钥
     * @return 连接密码
     */
    private static String buildPassword(String clientId, String productKey, String deviceSn, String deviceSecret) {
        String plaintextPassword = StrUtil.format(PASSWORD_TEMPLATE, clientId, productKey, deviceSn, deviceSecret);
        return DigestUtil.hmac(HmacAlgorithm.HmacSHA256, deviceSecret.getBytes())
                .digestHex(plaintextPassword);
    }

}
