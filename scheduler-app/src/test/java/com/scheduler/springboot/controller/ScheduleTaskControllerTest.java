package com.scheduler.springboot.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.springboot.domain.ScheduleTask;
import com.scheduler.springboot.repo.ScheduleTaskRepository;
import com.scheduler.springboot.schedulerapp.SchedulerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={SchedulerApplication.class, ScheduleTaskController.class})
@AutoConfigureMockMvc
public class ScheduleTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ScheduleTaskRepository scheduleTaskRepository;

    private List<ScheduleTask> tasks;

    @BeforeEach
    void setUp() {
        this.tasks = new ArrayList<>();
        this.tasks.add(new ScheduleTask(1L, "println 'Task 1 running'", "Task 1", "* /1 * * * *"));
        this.tasks.add(new ScheduleTask(2L, "println 'Task 2 running'", "Task 2", "30 3 * * * *"));
        this.tasks.add(new ScheduleTask(3L, "println 'Task 3 running'", "Task 3", "* 59 19 * * *"));

    }

    @Test
    void shouldFetchAllTasks() throws Exception {

        given(scheduleTaskRepository.findAll()).willReturn(tasks);
        this.mockMvc.perform(get("/tasks"))
                 .andExpect(status().isOk());

    }

    @Test
    void shouldFetchOneTaskById() throws Exception {
        long taskId= 1;
        ScheduleTask task = new ScheduleTask(1, "println 'Task 1 running'", "Task 1", "* /1 * * * *");
        given(scheduleTaskRepository.findById(taskId)).willReturn(Optional.of(task));
        this.mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk());

    }



    @Test
    void shouldCreateNewTask() throws Exception {

         ScheduleTask postedTask = new ScheduleTask(4, "Task 4", "* /3 * * * *", "println 'Task 4 running'");
         ScheduleTask returnedTask = new ScheduleTask(4, "Task 4", "* /3 * * * *", "println 'Task 4 running'");

        doReturn(returnedTask).when(scheduleTaskRepository).save(any());

         String json = new ObjectMapper().writeValueAsString(postedTask);

         //Execute the POST request
        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        }



}
