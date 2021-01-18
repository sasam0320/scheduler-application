package com.scheduler.springboot.controller;

import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.exception.TaskNotFoundException;
import com.scheduler.springboot.repo.ScheduleTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class ScheduleTaskController {

    @Autowired
    private ScheduleTaskRepository scheduleTaskRepository;

    @GetMapping
    public List<ScheduleTask> getAllTasks() {

        return scheduleTaskRepository.findAll();
    }

    @GetMapping("/{taskId}")
    public ScheduleTask getTaskById(@PathVariable("taskId") long taskId) {

        Optional<ScheduleTask> task = scheduleTaskRepository.findById(taskId);

        try {

            if (!task.isPresent())
                throw new TaskNotFoundException("Task not found for this id : " + taskId);

        } catch (TaskNotFoundException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }


        return task.get();
    }

    @GetMapping("/search")
    public ScheduleTask getTaskByName(@RequestParam(value="name") String name) {

        Optional<ScheduleTask> task = scheduleTaskRepository.findByName(name);

        try {

            if (!task.isPresent())
                throw new TaskNotFoundException("Task not found with this name : " + name);

        } catch (TaskNotFoundException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }


        return task.get();
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public ScheduleTask createTask(@RequestBody Map<String, String> taskData) {

        String name = taskData.get("name");
        String recurrence = taskData.get("recurrence");
        String code = taskData.get("code");

        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setName(name);
        scheduleTask.setRecurrence(recurrence);
        scheduleTask.setCode(code);

        ScheduleTask savedTask = scheduleTaskRepository.save(scheduleTask);

        return savedTask;
    }

    @PutMapping(path = "/edit/{id}")
    public ScheduleTask editTask(@PathVariable("id") long taskId, @RequestBody Map<String, String> taskData) {

        ScheduleTask scheduleTask = null;

        try {

            scheduleTask = scheduleTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found for this id : " + taskId));


        } catch (TaskNotFoundException e) {
            e.getMessage();
        }

        scheduleTask.setName(taskData.get("name"));
        scheduleTask.setRecurrence(taskData.get("recurrence"));
        scheduleTask.setCode(taskData.get("code"));

        ScheduleTask updatedTask = scheduleTaskRepository.save(scheduleTask);

        return updatedTask;
    }

    @DeleteMapping(path = "/{id}")
    public ScheduleTask deleteTask(@PathVariable("id") long taskId) {

        ScheduleTask scheduleTask = null;

        try {

            scheduleTask = scheduleTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found for this id : " + taskId));


        } catch (TaskNotFoundException e) {
            e.getMessage();
        }

        scheduleTaskRepository.delete(scheduleTask);

        return scheduleTask;
    }



}
