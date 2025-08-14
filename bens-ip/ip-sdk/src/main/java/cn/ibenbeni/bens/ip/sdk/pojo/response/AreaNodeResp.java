package cn.ibenbeni.bens.ip.sdk.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 地区节点响应")
public class AreaNodeResp {

    @Schema(description = "区域编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private String areaCode;

    @Schema(description = "区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "陕西")
    private String areaName;

    /**
     * 子节点
     */
    private List<AreaNodeResp> children;

}
