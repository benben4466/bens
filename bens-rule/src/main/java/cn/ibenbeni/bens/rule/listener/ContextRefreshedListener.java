package cn.ibenbeni.bens.rule.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Spring 应用上下文已经完成刷新（初始化）过程
 * <p>触发时机:当ApplicationContext完成刷新，所有单例Bean已经被创建并初始化后触发。</p>
 * <p>
 * 事件作用:
 * 1.表示Spring容器已经准备好，可以开始执行依赖Bean的业务逻辑
 * 2.通常用于启动后需要执行的初始化任务，比如加载缓存、启动定时任务等
 * </p>
 */
@Slf4j
public abstract class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        eventCallback(event);
    }

    protected abstract void eventCallback(ContextRefreshedEvent event);

}
