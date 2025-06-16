package cn.ibenbeni.bens.validator.api.validators.unique;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.validator.api.context.RequestGroupContext;
import cn.ibenbeni.bens.validator.api.context.RequestParamContext;
import cn.ibenbeni.bens.validator.api.pojo.UniqueValidateParam;
import cn.ibenbeni.bens.validator.api.validators.unique.util.TableUniqueValueUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证表的的某个字段值是否在是唯一值
 *
 * @author: benben
 * @time: 2025/6/15 下午3:28
 */
public class TableUniqueValueValidator implements ConstraintValidator<TableUniqueValue, Object> {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 主键字段的名称
     */
    private String idFieldName;

    /**
     * 是否开启逻辑删除校验，默认是关闭的
     * <p>为何有此字段：若项目中某个表包含控制逻辑删除的字段，在进行唯一值校验的时候要排除这种状态的记录，所以需要用到这个功能</p>
     */
    private boolean excludeLogicDeleteItems;

    /**
     * 逻辑删除的字段名称
     */
    private String logicDeleteFieldName;

    /**
     * 默认逻辑删除的值（Y是已删除）
     */
    private String logicDeleteValue;

    @Override
    public void initialize(TableUniqueValue constraintAnnotation) {
        this.tableName = constraintAnnotation.tableName();
        this.columnName = constraintAnnotation.columnName();
        this.idFieldName = constraintAnnotation.idFieldName();
        this.excludeLogicDeleteItems = constraintAnnotation.excludeLogicDeleteItems();
        this.logicDeleteFieldName = constraintAnnotation.logicDeleteFieldName();
        this.logicDeleteValue = constraintAnnotation.logicDeleteValue();
    }

    @Override
    public boolean isValid(Object fieldValue, ConstraintValidatorContext context) {
        // 若字段值为空，则忽略
        if (ObjectUtil.isEmpty(fieldValue)) {
            return true;
        }

        // 获取当前执行的校验分组
        Class<?> validateGroupClass = RequestGroupContext.get();

        // 如果属于edit group，校验时需要排除当前修改的这条记录
        if (BaseRequest.edit.class.equals(validateGroupClass)) {
            UniqueValidateParam editParam = this.createEditParam(fieldValue);
            return TableUniqueValueUtil.getFiledUniqueFlag(editParam);
        }

        // 如果属于add group，则校验库中所有行
        if (BaseRequest.add.class.equals(validateGroupClass)) {
            UniqueValidateParam addParam = createAddParam(fieldValue);
            return TableUniqueValueUtil.getFiledUniqueFlag(addParam);
        }

        // 默认校验所有的行
        UniqueValidateParam addParam = createAddParam(fieldValue);
        return TableUniqueValueUtil.getFiledUniqueFlag(addParam);
    }

    /**
     * 创建修改的参数校验
     *
     * @param fieldValue 被校验字段的值
     */
    private UniqueValidateParam createEditParam(Object fieldValue) {
        // 获取请求字段中ID的值
        Dict requestParam = RequestParamContext.get();
        // 获取ID字段的驼峰命名法
        String camelCaseIdFieldName = StrUtil.toCamelCase(idFieldName);

        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .idFieldName(idFieldName)
                .excludeCurrentRecord(Boolean.TRUE)
                .id(requestParam.getLong(camelCaseIdFieldName))
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue).build();
    }

    /**
     * 创建校验新增的参数
     *
     * @param fieldValue 被校验字段的值
     */
    private UniqueValidateParam createAddParam(Object fieldValue) {
        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .excludeCurrentRecord(Boolean.FALSE)
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue)
                .build();
    }

}
