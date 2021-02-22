package com.scheduler.springboot.domain;

import com.scheduler.springboot.utils.GroovyInstance;
import groovy.lang.GroovyShell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity(name = "ScheduleTask")
@Table(name = "task")
public class ScheduleTask implements Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String recurrence;
    private String code;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        GroovyShell shell = GroovyInstance.getShell();
        String script = this.getCode();
        log.info("Executing script for the task " + name);
        shell.evaluate(script);

    }
}
