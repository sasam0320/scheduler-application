package com.scheduler.springboot.schedulerapp;

import com.scheduler.springboot.config.CorsConfig;
import com.scheduler.springboot.config.SchedulerJobConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = {CorsConfig.class, SchedulerJobConfig.class})
@ComponentScan(basePackages = "com.scheduler.springboot.controller")
@EnableJpaRepositories("com.scheduler.springboot.repo")
@EntityScan(basePackages = "com.scheduler.springboot.domain")
public class SchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class, args);
	}

}
