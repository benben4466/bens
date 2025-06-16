package cn.ibenbeni.bens.validator.api.validators.unique.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.DbOperatorApi;
import cn.ibenbeni.bens.db.api.context.DbOperatorContext;
import cn.ibenbeni.bens.validator.api.exception.ParamValidateException;
import cn.ibenbeni.bens.validator.api.exception.enums.ValidatorExceptionEnum;
import cn.ibenbeni.bens.validator.api.pojo.UniqueValidateParam;

/**
 * 判断表中字段是否是唯一值的业务封装
 *
 * @author: benben
 * @time: 2025/6/16 下午10:19
 */
public class TableUniqueValueUtil {

    /**
     * 判断表中某个字段是否已经存在该值
     *
     * @param uniqueValidateParam 参数
     * @return true=存在，false=不存在
     */
    public static boolean getFiledUniqueFlag(UniqueValidateParam uniqueValidateParam) {
        try {
            return doValidate(uniqueValidateParam);
        } catch (Exception ex) {
            throw new ParamValidateException(ValidatorExceptionEnum.UNIQUE_VALIDATE_SQL_ERROR, ex.getMessage());
        }
    }

    private static boolean doValidate(UniqueValidateParam uniqueValidateParam) {
        DbOperatorApi dbOperatorApi = DbOperatorContext.me();

        int resultCount = 0;

        // 参数校验
        paramValidate(uniqueValidateParam);

        // 不排除当前记录，不排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {
            String sqlTemplate = "select count(*) from {} where {} = {0}";
            String finalSql = StrUtil.format(sqlTemplate, uniqueValidateParam.getTableName(), uniqueValidateParam.getColumnName());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue());
        }

        // 不排除当前记录，排除逻辑删除的内容
        if (!uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {
            String sqlTemplate = "select count(*) from {} where {} = {0}  and ({} is null or {} <> '{}')";
            String finalSql = StrUtil.format(sqlTemplate,
                    uniqueValidateParam.getTableName(),
                    uniqueValidateParam.getColumnName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteValue());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue());
        }

        // 排除当前记录，不排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && !uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // ID判空
            paramIdValidate(uniqueValidateParam);

            String sqlTemplate = "select count(*) from {} where {} = {0} and {} <> {1}";
            String finalSql = StrUtil.format(sqlTemplate, uniqueValidateParam.getTableName(), uniqueValidateParam.getColumnName(), uniqueValidateParam.getIdFieldName());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue(), uniqueValidateParam.getId());
        }

        // 排除当前记录，排除逻辑删除的内容
        if (uniqueValidateParam.getExcludeCurrentRecord()
                && uniqueValidateParam.getExcludeLogicDeleteItems()) {

            // ID判空
            paramIdValidate(uniqueValidateParam);

            String sqlTemplate = "select count(*) from {} where {} = {0} and {} <> {1} and ({} is null or {} <> '{}')";
            String finalSql = StrUtil.format(sqlTemplate,
                    uniqueValidateParam.getTableName(),
                    uniqueValidateParam.getColumnName(),
                    uniqueValidateParam.getIdFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteFieldName(),
                    uniqueValidateParam.getLogicDeleteValue());
            resultCount = dbOperatorApi.selectCount(finalSql, uniqueValidateParam.getValue(), uniqueValidateParam.getId());
        }

        // 如果大于0，代表不是唯一的当前校验的值
        return resultCount <= 0;
    }

    /**
     * 校验必要参数
     */
    private static void paramValidate(UniqueValidateParam uniqueValidateParam) {
        // 表名
        if (StrUtil.isBlank(uniqueValidateParam.getTableName())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue注解上tableName属性为空");
        }
        // 列名
        if (StrUtil.isBlank(uniqueValidateParam.getColumnName())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue注解上columnName属性为空");
        }
        // 值
        if (ObjectUtil.isEmpty(uniqueValidateParam.getValue())) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, "@TableUniqueValue被校验属性的值为空");
        }
    }

    /**
     * 校验ID参数
     */
    private static void paramIdValidate(UniqueValidateParam uniqueValidateParam) {
        if (uniqueValidateParam.getId() == null) {
            throw new ParamValidateException(ValidatorExceptionEnum.TABLE_UNIQUE_VALIDATE_ERROR, StrUtil.toCamelCase(uniqueValidateParam.getIdFieldName()) + "参数值为空");
        }
    }

}
