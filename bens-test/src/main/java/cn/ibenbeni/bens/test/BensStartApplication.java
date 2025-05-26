package cn.ibenbeni.bens.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author benben
 * @date 2025/5/21  下午8:38
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"cn.ibenbeni"})
public class BensStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BensStartApplication.class, args);
        log.info(BensStartApplication.class.getSimpleName() + " is success!");
    }

}
