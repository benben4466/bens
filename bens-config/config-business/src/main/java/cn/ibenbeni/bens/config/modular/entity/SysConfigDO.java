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
 * 参数配置实体
 *
 * @author: benben
 * @time: 2025/6/18 上午10:20
 */
@TableName(value = "sys_config", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private Long configId;

    /**
     * 参数配置类型编码
     */
    @TableField("config_type_code")
    private String configTypeCode;

    /**
     * 参数配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 参数配置编码
     */
    @TableField("config_code")
    private String configCode;

    /**
     * 参数配置值
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 系统参数类型
     */
    @TableField("config_type")
    private Integer configType;

    /**
     * 显示排序
     */
    @TableField("config_sort")
    private BigDecimal configSort;

    /**
     * 是否可见
     */
    @TableField("visible_flag")
    private Boolean visibleFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
