package cn.ibenbeni.bens.ds.sdk;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.ds.api.constants.DatasourceContainerConstants;
import cn.ibenbeni.bens.ds.api.context.CurrentDataSourceContext;
import cn.ibenbeni.bens.ds.sdk.context.DataSourceContext;

import javax.sql.DataSource;

/**
 * 动态数据源实现
 *
 * @author: benben
 * @time: 2025/6/25 下午3:13
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected DataSource determineDataSource() {
        // 获取当前数据源名称
        String dataSourceName = CurrentDataSourceContext.getDataSourceName();

        // 若无值，则默认使用主数据源
        if (StrUtil.isBlank(dataSourceName)) {
            dataSourceName = DatasourceContainerConstants.MASTER_DATASOURCE_NAME;
        }

        // 获取对应的数据源
        return DataSourceContext.getDataSources().get(dataSourceName);
    }

}
