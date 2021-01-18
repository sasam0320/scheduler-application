package com.scheduler.springboot.domain;


import com.scheduler.springboot.utils.GroovyInstance;
import groovy.lang.GroovyShell;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleJob implements Runnable {

    private String jobName;

    private ScheduleTask task;

    @Override
    public void run() {

        GroovyShell shell = GroovyInstance.getShell();
        String script = this.task.getCode();
        shell.evaluate(script);

    }
}
