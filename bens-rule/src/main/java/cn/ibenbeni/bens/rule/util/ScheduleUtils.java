package cn.ibenbeni.bens.rule.util;

import org.springframework.scheduling.support.CronExpression;

/**
 * 定时任务工具类
 */
public class ScheduleUtils {

    /**
     * 校验 CRON 表达式
     *
     * @param cronExpression CRON 表达式
     * @return true=校验通过；false=校验失败；
     */
    public static boolean isValidCronExpression(String cronExpression) {
        try {
            return CronExpression.isValidExpression(cronExpression);  // Quartz 提供的验证方法
        } catch (Exception ex) {
            return false;
        }
    }

}
