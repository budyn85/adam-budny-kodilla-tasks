package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {
    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "title1", "content1");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals((Long)1L, task.getId());
        assertEquals("title1", task.getTitle());
        assertEquals("content1", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(7L, "title2", "content2");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals((Long)7L, taskDto.getId());
        assertEquals("title2", taskDto.getTitle());
        assertEquals("content2", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoListWhenListIsEmpty() {
        //Given
        List<Task> taskList = new ArrayList<>();

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(0, taskDtoList.size());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = Arrays.asList(
                new Task(1L, "Title1", "Content1"),
                new Task(2L, "Title2", "Content2"),
                new Task(3L, "Title3", "Content3")
        );

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        TaskDto taskDto1 = taskDtoList.get(0);
        TaskDto taskDto2 = taskDtoList.get(1);
        TaskDto taskDto3 = taskDtoList.get(2);

        //Then
        assertEquals(3, taskDtoList.size());

        assertEquals("Title1", taskDto1.getTitle());
        assertEquals("Title2", taskDto2.getTitle());
        assertEquals("Title3", taskDto3.getTitle());

        assertEquals("Content1", taskDto1.getContent());
        assertEquals("Content2", taskDto2.getContent());
        assertEquals("Content3", taskDto3.getContent());
    }
}
