package com.scheduler.springboot.controller;

import com.scheduler.springboot.domain.ScheduleJob;
import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.scheduler.SchedulerJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class ScheduleJobController {

    @Autowired
    private SchedulerJobExecutor schedulerJobExecutor ;


    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    public void runJobs(@RequestBody List<ScheduleTask> tasks){

        try{
            List<ScheduleJob> jobs = this.createJobs(tasks);
            schedulerJobExecutor.run(jobs);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<ScheduleJob> createJobs(List<ScheduleTask> tasks){

        List<ScheduleJob> jobs = new ArrayList<>();

            tasks.stream().forEach(task -> {
                int i = 0;
                ScheduleJob job = new ScheduleJob("Job " + i + "", task);
                jobs.add(job);
                i++;
            });

         return jobs;
    }
}
