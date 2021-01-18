package com.scheduler.springboot.config;

import com.scheduler.springboot.scheduler.SchedulerJobExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import java.util.concurrent.ScheduledExecutorService;


@Configuration
@ComponentScan(basePackages = "com.scheduler.springboot.scheduler", basePackageClasses = { SchedulerJobExecutor.class })
public class SchedulerJobConfig {

    @Bean(name = "scheduler")
    public ScheduledExecutorService scheduler(){

        ScheduledExecutorFactoryBean executorFactoryBean = new ScheduledExecutorFactoryBean();
        executorFactoryBean.setThreadNamePrefix("SchedulerJobExecutor");
        executorFactoryBean.setPoolSize(5);
        executorFactoryBean.afterPropertiesSet();
        return executorFactoryBean.getObject();
    }

}
