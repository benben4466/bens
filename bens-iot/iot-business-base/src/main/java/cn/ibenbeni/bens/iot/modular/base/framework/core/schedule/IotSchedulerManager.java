package cn.ibenbeni.bens.iot.modular.base.framework.core.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * IOT模块-Scheduler 管理类，基于 Quartz 实现
 */
@Slf4j
public class IotSchedulerManager {

    private static final String SCHEDULER_NAME = "iotScheduler";

    private final SchedulerFactoryBean schedulerFactoryBean;

    private Scheduler scheduler;

    public IotSchedulerManager(DataSource dataSource, ApplicationContext applicationContext) {
        // SchedulerFactoryBean：负责配置和初始化 Quartz 调度器
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // SpringBeanJobFactory：让 Quartz 的 Job 能够自动注入 Spring 管理的 Bean
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        // 通过 SpringBeanJobFactory 设置 ApplicationContext 让 Quartz 任务访问到 Spring Bean
        jobFactory.setApplicationContext(applicationContext);
        // 配置 JobFactory
        schedulerFactoryBean.setJobFactory(jobFactory);
        // 设置 Quartz 调度器是否在应用启动时自动启动
        schedulerFactoryBean.setAutoStartup(true);
        // 设置 Scheduler 名称
        schedulerFactoryBean.setSchedulerName(SCHEDULER_NAME);
        // 设置数据源，因为要使用 Quartz 相关表
        schedulerFactoryBean.setDataSource(dataSource);
        // 设置配置在应用关闭时，等待所有任务完成再关闭调度器
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        Properties properties = new Properties();
        schedulerFactoryBean.setQuartzProperties(properties);
        // 2. 参考 application-local.yaml 配置文件
        // 2.1 Scheduler 相关配置
        // instanceName：调度器的实例名称
        properties.put("org.quartz.scheduler.instanceName", SCHEDULER_NAME);
        // instanceId：调度器的实例 ID
        properties.put("org.quartz.scheduler.instanceId", "AUTO");
        // 2.2 JobStore 相关配置
        // jobStore.class：指定 Quartz 使用的数据存储类型
        properties.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        // jobStore.isClustered：设置为 true 表示启用 Quartz 的集群模式
        properties.put("org.quartz.jobStore.isClustered", "true");
        // jobStore.clusterCheckinInterval：集群节点间的心跳检测间隔时间（单位：毫秒）
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        // jobStore.misfireThreshold：设置任务错过执行时的最大时间（单位：毫秒）
        properties.put("org.quartz.jobStore.misfireThreshold", "60000");
        // 2.3 线程池相关配置
        properties.put("org.quartz.threadPool.threadCount", "25");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("[start][IotScheduler 初始化开始]");
        schedulerFactoryBean.afterPropertiesSet();
        schedulerFactoryBean.start();
        this.scheduler = schedulerFactoryBean.getScheduler();
        log.info("[start][IotScheduler 初始化完成]");
    }

    @PreDestroy
    public void destroy() {
        log.info("[stop][IotScheduler 关闭开始]");
        schedulerFactoryBean.stop();
        this.scheduler = null;
        log.info("[stop][IotScheduler 关闭完成]");
    }

    // region Job方法

    /**
     * 添加 Job 到 Quartz 中
     *
     * @param jobClass       任务处理器的类
     * @param jobName        任务名
     * @param cronExpression CRON 表达式
     * @param jobDataMap     任务数据
     * @throws SchedulerException 添加异常
     */
    public void addJob(Class<? extends Job> jobClass, String jobName,
                       String cronExpression, Map<String, Object> jobDataMap)
            throws SchedulerException {
        // 创建 JobDetail 对象
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .usingJobData(new JobDataMap(jobDataMap))
                .withIdentity(jobName).build();
        // 创建 Trigger 对象
        Trigger trigger = this.buildTrigger(jobName, cronExpression);
        // 新增 Job 调度
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 添加或更新 Job 到 Quartz 中
     *
     * @param jobClass       任务处理器的类
     * @param jobName        任务名
     * @param cronExpression CRON 表达式
     * @param jobDataMap     任务数据
     * @throws SchedulerException 添加异常
     */
    public void addOrUpdateJob(Class<? extends Job> jobClass, String jobName,
                               String cronExpression, Map<String, Object> jobDataMap)
            throws SchedulerException {
        if (scheduler.checkExists(new JobKey(jobName))) {
            this.updateJob(jobName, cronExpression);
        } else {
            this.addJob(jobClass, jobName, cronExpression, jobDataMap);
        }
    }

    /**
     * 删除 Quartz 中的 Job
     *
     * @param jobName 任务名
     * @throws SchedulerException 删除异常
     */
    public void deleteJob(String jobName) throws SchedulerException {
        // 暂停 Trigger 对象
        scheduler.pauseTrigger(new TriggerKey(jobName));
        // 取消并删除 Job 调度
        scheduler.unscheduleJob(new TriggerKey(jobName));
        scheduler.deleteJob(new JobKey(jobName));
    }

    /**
     * 更新 Job 到 Quartz
     *
     * @param jobName        任务名
     * @param cronExpression CRON 表达式
     * @throws SchedulerException 更新异常
     */
    public void updateJob(String jobName, String cronExpression)
            throws SchedulerException {
        // 创建新 Trigger 对象
        Trigger newTrigger = this.buildTrigger(jobName, cronExpression);
        // 修改调度
        scheduler.rescheduleJob(new TriggerKey(jobName), newTrigger);
    }

    /**
     * 暂停 Quartz 中的 Job
     *
     * @param jobName 任务名
     * @throws SchedulerException 暂停异常
     */
    public void pauseJob(String jobName) throws SchedulerException {
        scheduler.pauseJob(new JobKey(jobName));
    }

    private Trigger buildTrigger(String jobName, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

    // endregion

}
