package com.scheduler.springboot.scheduler;

import com.scheduler.springboot.domain.ScheduleTask;

import java.util.List;

public interface TaskRunner {
    void run(List<ScheduleTask> taskList);
}
