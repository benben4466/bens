package cn.ibenbeni.bens.validator.api.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 数据库列表值唯一验证参数
 *
 * @author: benben
 * @time: 2025/6/15 下午4:58
 */
@Data
@Builder
public class UniqueValidateParam {

    /**
     * 表名
     */
    String tableName;

    /**
     * 列名
     */
    String columnName;

    /**
     * 被参数校验时候的字段的值
     */
    Object value;

    /**
     * 校验时，是否排除当前的记录
     */
    Boolean excludeCurrentRecord;

    /**
     * 主键ID的字段名
     */
    String idFieldName;

    /**
     * 当前记录的主键ID
     */
    Long id;

    /**
     * 排除所有被逻辑删除的记录的控制
     */
    Boolean excludeLogicDeleteItems;

    /**
     * 逻辑删除的字段名
     */
    String logicDeleteFieldName;

    /**
     * 逻辑删除的字段的值
     */
    String logicDeleteValue;

}
