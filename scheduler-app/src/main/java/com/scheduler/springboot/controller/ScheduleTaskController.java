package com.scheduler.springboot.controller;

import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.dto.ScheduleTaskDTO;
import com.scheduler.springboot.exception.TaskNotFoundException;
import com.scheduler.springboot.repo.ScheduleTaskRepository;
import com.scheduler.springboot.scheduler.ScheduleTaskExecutor;
import com.scheduler.springboot.utils.ScheduleTaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/tasks")
public class ScheduleTaskController {


    @Autowired
    private ScheduleTaskRepository scheduleTaskRepository;

    @Autowired
    private ScheduleTaskExecutor scheduleTaskExecutor;

    @GetMapping
    public List<ScheduleTask> getAllTasks() {

        return scheduleTaskRepository.findAll();
    }

    @GetMapping("/{taskId}")
    public ScheduleTask getTaskById(@PathVariable("taskId") long taskId) {

        ScheduleTask task = scheduleTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found with this id : " + taskId));

        return task;
    }

    @GetMapping("/search")
    public ScheduleTask getTaskByName(@RequestParam(value = "name") String name) {

        ScheduleTask task = scheduleTaskRepository.findByName(name).orElseThrow(() -> new TaskNotFoundException("Task not found with this name : " + name));

        return task;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleTask createTask(@RequestBody ScheduleTaskDTO taskDto) {

        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setName(taskDto.getName());
        scheduleTask.setRecurrence(taskDto.getRecurrence());
        scheduleTask.setCode(taskDto.getCode());

        ScheduleTask savedTask = scheduleTaskRepository.save(scheduleTask);

        return savedTask;
    }

    @PostMapping(path = "/run", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void runTasks(@RequestBody List<ScheduleTaskDTO> dtoList) {

        List<ScheduleTask> tasks;

        try {
            tasks = ScheduleTaskUtil.convertList(dtoList);
            scheduleTaskExecutor.run(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PutMapping(path = "/{id}")
    public ScheduleTask editTask(@PathVariable("id") long taskId, @RequestBody ScheduleTaskDTO taskDto) {

        ScheduleTask scheduleTask = scheduleTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found for this id : " + taskId));

        scheduleTask.setName(taskDto.getName());
        scheduleTask.setRecurrence(taskDto.getRecurrence());
        scheduleTask.setCode(taskDto.getCode());

        ScheduleTask updatedTask = scheduleTaskRepository.save(scheduleTask);

        return updatedTask;
    }

    @DeleteMapping(path = "/{id}")
    public ScheduleTask deleteTask(@PathVariable("id") long taskId) {

        ScheduleTask scheduleTask = scheduleTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found for this id : " + taskId));

        scheduleTaskRepository.delete(scheduleTask);

        return scheduleTask;
    }


}
