package cn.ibenbeni.bens.log.operatelog.config;

import cn.ibenbeni.bens.log.operatelog.service.DefaultLogRecordServiceImpl;
import com.mzt.logapi.service.ILogRecordService;
import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 操作日志配置类
 */
@EnableLogRecord(tenant = "") // bizlog操作日志组件,应该用不上
@Configuration
public class BensOperateLogConfiguration {

    /**
     * 注入存储操作日志Bean
     */
    @Bean
    @Primary
    public ILogRecordService defaultLogRecordServiceImpl() {
        return new DefaultLogRecordServiceImpl();
    }

}
