package com.scheduler.springboot.utils;

import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.dto.ScheduleTaskDTO;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTaskUtil {

    public static List<ScheduleTask> convertList(List<ScheduleTaskDTO> dtoList){

        List<ScheduleTask> scheduleTasks = new ArrayList<>(dtoList.size());

        dtoList.stream().forEach(dto ->

        {
            ScheduleTask task = new ScheduleTask(dto.getId(), dto.getName(), dto.getRecurrence(), dto.getCode());
            scheduleTasks.add(task);

        });

        return scheduleTasks;
    }
}
