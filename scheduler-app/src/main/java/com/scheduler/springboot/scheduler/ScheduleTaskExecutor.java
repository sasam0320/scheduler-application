package com.scheduler.springboot.scheduler;

import com.scheduler.springboot.domain.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("scheduleTaskExecutor")
public class ScheduleTaskExecutor implements TaskRunner {


    @Autowired
    private Scheduler scheduler;

    private int count = 1;

    public ScheduleTaskExecutor() {

    }

    @Override
    public void run(List<ScheduleTask> tasks) {

        tasks.stream().forEach((task) -> {


            try {

                JobDataMap data = new JobDataMap();
                data.put("id", task.getId());
                data.put("name", task.getName());
                data.put("recurrence", task.getRecurrence());
                data.put("code", task.getCode());

                JobDetail job = JobBuilder.newJob().ofType(ScheduleTask.class)
                        .withIdentity("Schedule_task_" + this.count)
                        .withDescription("Invoke schedule task " + this.count)
                        .setJobData(data)
                        .storeDurably()
                        .build();


                Trigger taskTrigger = TriggerBuilder.newTrigger().forJob("Schedule_task_" + this.count)
                        .withIdentity("Schedule_trigger_" + this.count++)
                        .withSchedule(CronScheduleBuilder.cronSchedule(task.getRecurrence()))
                        .startNow()
                        .build();


                scheduler.scheduleJob(job, taskTrigger);


                log.info("The job for the task " + task.getName() + " starts at " + taskTrigger.getStartTime());
                log.info("Next firing time at " + taskTrigger.getNextFireTime());


            } catch (Exception e) {

                e.printStackTrace();
            }

        });

        //scheduler.clear();
        //taskExecutor.shutdown();

    }

}
