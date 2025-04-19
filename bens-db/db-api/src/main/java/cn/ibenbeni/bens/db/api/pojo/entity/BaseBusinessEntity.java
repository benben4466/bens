package cn.ibenbeni.bens.db.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础业务性质的实体
 * <p>具有业务逻辑删除</p>
 *
 * @author benben
 * @date 2025/4/18  下午12:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseBusinessEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 删除标记：Y-已删除，N-未删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private String delFlag;

}
