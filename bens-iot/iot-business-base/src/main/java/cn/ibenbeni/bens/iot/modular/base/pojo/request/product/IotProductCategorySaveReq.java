package cn.ibenbeni.bens.iot.modular.base.pojo.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - IoT产品分类新增/修改入参")
public class IotProductCategorySaveReq {

    @Schema(description = "产品分类ID", example = "10")
    private Long categoryId;

    @NotBlank(message = "产品分类名称不能为空")
    @Schema(description = "产品分类名称", example = "笨笨烟感器")
    private String categoryName;

    @NotBlank(message = "产品分类编码不能为空")
    @Schema(description = "产品分类编码", example = "benben_test")
    private String categoryCode;

    @NotNull(message = "产品分类排序不能为空")
    @Schema(description = "产品分类排序", example = "1.0")
    private Double categorySort;

    @NotNull(message = "产品分类状态不能为空")
    @Schema(description = "产品分类状态", example = "1")
    private Integer statusFlag;

    @NotNull(message = "是否系统内置不能为空")
    @Schema(description = "是否系统内置", example = "1")
    private Integer isSys;

    @Schema(description = "备注")
    private String remark;

}
