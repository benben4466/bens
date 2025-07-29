package cn.ibenbeni.bens.config.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 参数配置类型实体
 *
 * @author: benben
 * @time: 2025/6/18 下午10:17
 */
@TableName(value = "sys_config_type", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigTypeDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "config_type_id", type = IdType.ASSIGN_ID)
    private Long configTypeId;

    /**
     * 参数配置类型名称
     */
    @TableField("config_type_name")
    private String configTypeName;

    /**
     * 参数配置类型编码
     */
    @TableField("config_type_code")
    private String configTypeCode;

    /**
     * 参数配置类型
     */
    @TableField("config_type")
    private Integer configType;

    /**
     * 是否可见(Y=可见，N=非可见)
     */
    @TableField("visible_flag")
    private Boolean visibleFlag;

    /**
     * 显示排序
     */
    @TableField("config_type_sort")
    private BigDecimal configTypeSort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
