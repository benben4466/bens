package cn.ibenbeni.bens.ds.api.context;

/**
 * 当前使用动态数据源上下文
 * <p>利用ThreadLocal缓存当前请求的数据源</p>
 *
 * @author: benben
 * @time: 2025/6/25 下午2:35
 */
public class CurrentDataSourceContext {

    /**
     * 当前数据源名称
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceName(String dataSourceName) {
        DATASOURCE_CONTEXT_HOLDER.set(dataSourceName);
    }

    public static String getDataSourceName() {
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceName() {
        DATASOURCE_CONTEXT_HOLDER.remove();
    }

}
