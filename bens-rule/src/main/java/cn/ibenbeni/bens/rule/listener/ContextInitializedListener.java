package cn.ibenbeni.bens.rule.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Context初始化监听器
 * <p>ApplicationContextInitializedEvent：在ApplicationContext初始化完成但未加载任何Bean定义时触发</p>
 *
 * @author: benben
 * @time: 2025/6/25 下午4:05
 */
@Slf4j
public abstract class ContextInitializedListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        // 若是注解配置上下文，则忽略
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        // 执行具体业务逻辑
        this.eventCallback(event);
    }

    /**
     * 监听器具体的业务逻辑
     */
    public abstract void eventCallback(ApplicationContextInitializedEvent event);

}
