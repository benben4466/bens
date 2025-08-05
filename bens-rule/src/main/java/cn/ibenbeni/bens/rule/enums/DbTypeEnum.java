package cn.ibenbeni.bens.rule.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 不同数据库类型的枚举
 * <p>数据库连接处不同数据库的标识</p>
 *
 * @author: benben
 * @time: 2025/6/16 下午10:23
 */
@Getter
public enum DbTypeEnum implements ReadableEnum<DbTypeEnum> {

    /**
     * MySQL
     */
    MYSQL("jdbc:mysql", "mysql", "select 1"),

    /**
     * PostgreSQL
     */
    PG_SQL("jdbc:postgresql", "pgsql", "select version()"),
    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(DbTypeEnum::getUrlWords).toArray(String[]::new);

    /**
     * spring.datasource.url中包含的关键字
     */
    private final String urlWords;

    /**
     * mapping.xml使用databaseId="xxx"来标识的关键字
     */
    private final String xmlDatabaseId;

    /**
     * validateQuery所使用的语句
     */
    private final String connectionTestQuery;

    DbTypeEnum(String urlWords, String xmlDatabaseId, String validateQuery) {
        this.urlWords = urlWords;
        this.xmlDatabaseId = xmlDatabaseId;
        this.connectionTestQuery = validateQuery;
    }

    /**
     * 通过数据库连接的URL判断是哪种数据库
     *
     * @param url 数据库连接的URL
     * @return 枚举名称，如MYSQL
     */
    public static String getTypeByUrl(String url) {
        if (url == null) {
            return MYSQL.name();
        }

        for (DbTypeEnum value : DbTypeEnum.values()) {
            if (url.contains(value.getUrlWords())) {
                return value.name();
            }
        }

        return MYSQL.name();
    }

    @Override
    public Object getKey() {
        return urlWords;
    }

    @Override
    public Object getName() {
        return xmlDatabaseId;
    }

    @Override
    public DbTypeEnum parseToEnum(String urlWords) {
        return ArrayUtil.firstMatch(item -> item.getUrlWords().equals(urlWords), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
