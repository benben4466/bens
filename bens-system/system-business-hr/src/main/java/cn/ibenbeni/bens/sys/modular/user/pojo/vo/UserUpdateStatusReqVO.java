package cn.ibenbeni.bens.sys.modular.user.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户更新状态请求参数
 *
 * @author: benben
 * @time: 2025/7/6 下午2:43
 */
@Data
public class UserUpdateStatusReqVO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色编号不能为空")
    private Long userId;

    /**
     * 用户状态
     * <p>状态枚举：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @Schema(description = "用户状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer statusFlag;

}
