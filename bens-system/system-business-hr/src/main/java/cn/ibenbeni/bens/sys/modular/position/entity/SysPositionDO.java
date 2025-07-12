package cn.ibenbeni.bens.sys.modular.position.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 职位信息实体
 *
 * @author: benben
 * @time: 2025/7/12 下午1:28
 */
@TableName(value = "sys_position", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysPositionDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "position_id", type = IdType.ASSIGN_ID)
    private Long positionId;

    /**
     * 职位名称
     */
    @TableField("position_name")
    private String positionName;

    /**
     * 职位编码
     */
    @TableField("position_code")
    private String positionCode;

    /**
     * 职位排序
     */
    @TableField("position_sort")
    private BigDecimal positionSort;

    /**
     * 职位状态
     * <p>枚举类型: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
