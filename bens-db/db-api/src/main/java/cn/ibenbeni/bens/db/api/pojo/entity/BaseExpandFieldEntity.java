package cn.ibenbeni.bens.db.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 基础业务性质的实体
 *
 * @author benben
 * @date 2025/4/18  下午12:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseExpandFieldEntity extends BaseBusinessEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 拓展字段
     */
    @TableField(value = "expand_field", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> expandField;

}
