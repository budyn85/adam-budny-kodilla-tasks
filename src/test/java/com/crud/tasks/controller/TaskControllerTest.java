package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

//    @Rule
//    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldFetchEmptyTaskList() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        when(service.getAllTasks()).thenReturn(taskList);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTaskList() throws Exception {
        //Given
        List<Task> taskList = Arrays.asList(
                new Task(1L, "Title1", "Content1"),
                new Task(2L, "Title2", "Content2"),
                new Task(3L, "Title3", "Content3")
        );

        List<TaskDto> taskDtoList = Arrays.asList(
                new TaskDto(1L, "Title1", "Content1"),
                new TaskDto(2L, "Title2", "Content2"),
                new TaskDto(3L, "Title3", "Content3")
        );

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].content", is("Content1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Title2")))
                .andExpect(jsonPath("$[1].content", is("Content2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("Title3")))
                .andExpect(jsonPath("$[2].content", is("Content3")));
    }

    @Test
    public void testGetTaskWhenTaskIdExist() throws Exception {
        //Given
        Task task = new Task(1L, "Title1", "Content1");

        TaskDto taskDto = new TaskDto(1L, "Title1", "Content1");

        when(service.getTask(1L)).thenReturn(ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title1")))
                .andExpect(jsonPath("$.content", is("Content1")));
    }

    @Test
    public void shouldDeleteTaskWhenTaskIdExist() throws Exception {
        //Given
        Task task = new Task(1L, "Title1", "Content1");

        when(service.getTask(1L)).thenReturn(ofNullable(task));

        //When & Then
        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(1L, "Title1", "Content1");

        TaskDto taskDto = new TaskDto(1L, "Title1", "Content1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title1")))
                .andExpect(jsonPath("$.content", is("Content1")));
    }
}
