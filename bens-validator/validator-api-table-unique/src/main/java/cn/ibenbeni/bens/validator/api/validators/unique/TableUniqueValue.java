package cn.ibenbeni.bens.validator.api.validators.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static cn.ibenbeni.bens.validator.api.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_NAME;
import static cn.ibenbeni.bens.validator.api.constants.ValidatorConstants.DEFAULT_LOGIC_DELETE_FIELD_VALUE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证表的的某个字段值是否在是唯一值
 *
 * @author: benben
 * @time: 2025/6/15 下午3:28
 */
@Constraint(validatedBy = TableUniqueValueValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableUniqueValue {

    /**
     * 表名
     */
    String tableName();

    /**
     * 列名
     */
    String columnName();

    /**
     * 表主键字段名
     */
    String idFieldName();

    /**
     * 是否开启逻辑删除校验，默认是关闭的
     * <p>为何有此字段：若项目中某个表包含控制逻辑删除的字段，在进行唯一值校验的时候要排除这种状态的记录，所以需要用到这个功能</p>
     */
    boolean excludeLogicDeleteItems() default false;

    /**
     * 逻辑删除的字段名称
     */
    String logicDeleteFieldName() default DEFAULT_LOGIC_DELETE_FIELD_NAME;

    /**
     * 默认逻辑删除的值（Y是已删除）
     */
    String logicDeleteValue() default DEFAULT_LOGIC_DELETE_FIELD_VALUE;

    String message() default "库中存在重复编码，请更换该编码值";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TableUniqueValue[] value();
    }

}
