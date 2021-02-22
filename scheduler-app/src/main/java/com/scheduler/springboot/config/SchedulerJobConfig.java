package com.scheduler.springboot.config;

import com.scheduler.springboot.scheduler.ScheduleTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@ComponentScan(basePackages = "com.scheduler.springboot.scheduler", basePackageClasses = { ScheduleTaskExecutor.class })
public class SchedulerJobConfig {

/*    @Bean(name = "scheduler")
    public ScheduledExecutorService scheduler(){

        ScheduledExecutorFactoryBean executorFactoryBean = new ScheduledExecutorFactoryBean();
        executorFactoryBean.setThreadNamePrefix("SchedulerJobExecutor");
        executorFactoryBean.setPoolSize(5);
        executorFactoryBean.afterPropertiesSet();
        return executorFactoryBean.getObject();
    }*/

    @Bean("scheduler")
    public Scheduler scheduler() {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            log.info("Scheduler " + scheduler.getSchedulerName() + " started.");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return scheduler;
    }

}
