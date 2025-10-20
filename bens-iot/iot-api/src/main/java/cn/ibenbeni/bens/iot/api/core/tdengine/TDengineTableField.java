package cn.ibenbeni.bens.iot.api.core.tdengine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tdengine表字段
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TDengineTableField {

    /**
     * 字段名 - TDengine 默认 ts 字段，默认会被 TDengine 创建
     */
    public static final String FIELD_TS = "ts";

    /**
     * 上报时间戳字段名
     */
    public static final String REPORT_TIME = "report_time";

    /**
     * 设备ID字段名
     */
    public static final String DEVICE_ID = "device_id";

    public static final String TYPE_TINYINT = "TINYINT";
    public static final String TYPE_TINYINT_UNSIGNED = "TINYINT UNSIGNED";
    public static final String TYPE_SMALLINT = "SMALLINT";
    public static final String TYPE_SMALLINT_UNSIGNED = "SMALLINT UNSIGNED";
    public static final String TYPE_INT = "INT";
    public static final String TYPE_BIGINT = "BIGINT";
    public static final String TYPE_BIGINT_UNSIGNED = "BIGINT UNSIGNED";
    public static final String TYPE_FLOAT = "FLOAT";
    public static final String TYPE_DOUBLE = "DOUBLE";
    public static final String TYPE_DECIMAL = "DECIMAL";
    public static final String TYPE_BOOL = "BOOL";
    public static final String TYPE_BINARY = "BINARY";
    public static final String TYPE_NCHAR = "NCHAR";
    public static final String TYPE_VARCHAR = "VARCHAR";
    public static final String TYPE_JSON = "JSON"; // 只能 Tag 使用
    public static final String TYPE_TIMESTAMP = "TIMESTAMP";
    public static final String TYPE_GEOMETRY = "GEOMETRY";
    public static final String TYPE_BLOB = "BLOB"; // 可变长的二进制数据（最大长度 4MB）

    /**
     * 字段长度 - VARCHAR 默认长度
     */
    public static final int LENGTH_VARCHAR = 1024;

    /**
     * 注释 - TAG 字段
     */
    public static final String NOTE_TAG = "TAG";

    /**
     * 字段名
     */
    private String field;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 注释
     */
    private String note;

    public TDengineTableField(String field, String type) {
        this.field = field;
        this.type = type;
    }

}
