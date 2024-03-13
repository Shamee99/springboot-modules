package org.shamee.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@SpringBootApplication
@MapperScan("org.**.dao")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 当前环境 '{}' !!! 端口 '[{}]' !!!\n\t" +
                        "----------------------------------------------------------",
                environment.getProperty("spring.application.name"),
                environment.getActiveProfiles(),
                environment.getProperty("server.port"));
    }

}