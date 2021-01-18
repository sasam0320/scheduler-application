package com.scheduler.springboot.scheduler;

import com.scheduler.springboot.domain.ScheduleJob;

import java.util.List;

public interface JobRunner {
    void run(List<ScheduleJob> jobList) throws Exception;
}
