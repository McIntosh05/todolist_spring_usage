package com.defatov.todolist_spring_usage.factory;

import com.defatov.todolist_spring_usage.dto.TaskDto;
import com.defatov.todolist_spring_usage.dto.TaskRequest;
import com.defatov.todolist_spring_usage.model.Priority;
import com.defatov.todolist_spring_usage.model.Task;
import com.defatov.todolist_spring_usage.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    private StateService stateService;

    @Autowired
    public TaskDtoFactory(StateService stateService) {
        this.stateService = stateService;
    }

    public TaskDto makeTaskDto(Task task) {

        return TaskDto.builder()
                .name(task.getName())
                .priority(task.getPriority().name())
                .state(task.getState().getName())
                .build();

    }

    public Task makeTaskEntity(TaskRequest taskRequest) {

        return Task.builder()
                .name(taskRequest.getName())
                .priority(Priority.valueOf(taskRequest.getPriority()))
                .state(stateService.getByName(taskRequest.getState()))
                .build();

    }

}
