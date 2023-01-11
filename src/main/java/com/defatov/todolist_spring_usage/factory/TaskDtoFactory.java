package com.defatov.todolist_spring_usage.factory;

import com.defatov.todolist_spring_usage.dto.TaskDto;
import com.defatov.todolist_spring_usage.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(Task task) {

        return TaskDto.builder()
                .name(task.getName())
                .priority(task.getPriority().name())
                .state(task.getState().getName())
                .build();

    }

}
