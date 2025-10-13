package cn.ibenbeni.bens.iot.modular.base.pojo.response.device;

import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - IoT设备响应")
public class IotDeviceResp {

    @Schema(description = "设备ID", example = "10")
    private Long deviceId;

    @Schema(description = "设备名称", example = "笨笨1号设备")
    private String deviceName;

    @Schema(description = "设备昵称", example = "笨笨1号设备昵称")
    private String deviceNickname;

    @Schema(description = "设备序列号", example = "A17600981283438UVI")
    private String deviceSn;

    @Schema(description = "设备图片", example = "https://www.baidu.com/link?url=r4BN")
    private String picUrl;

    @Schema(description = "产品ID", example = "101")
    private Long productId;

    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "设备类型", example = "0")
    private Integer deviceType;

    @Schema(description = "设备状态", example = "0")
    private Integer statusFlag;

    @Schema(description = "设备最后上线时间", example = "1760098162000")
    private Long onlineTime;

    @Schema(description = "最后离线时间", example = "1760098162000")
    private Long offlineTime;

    @Schema(description = "设备激活时间", example = "1760098162000")
    private Long activeTime;

    @Schema(description = "设备IP", example = "183.225.40.43")
    private String networkIp;

    @Schema(description = "设备密钥", example = "ni2Ntr6Et7Zdetcp")
    private String deviceSecret;

    @Schema(description = "设备位置的纬度", example = "116.36")
    private BigDecimal latitude;

    @Schema(description = "设备位置的经度", example = "116.36")
    private BigDecimal longitude;

    @Schema(description = "设备所在地址", example = "中国北京市朝阳区")
    private String deviceAddress;

    @Schema(description = "备注")
    private String remark;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
