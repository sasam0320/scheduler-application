package com.scheduler.springboot.dto;

import lombok.Data;

@Data
public class ScheduleTaskDTO {

    private long id;
    private String name;
    private String recurrence;
    private String code;
}
