package com.defatov.todolist_spring_usage.factory;

import com.defatov.todolist_spring_usage.dto.ToDoDto;
import com.defatov.todolist_spring_usage.dto.ToDoRequest;
import com.defatov.todolist_spring_usage.model.ToDo;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ToDoDtoFactory {

    public ToDoDto makeToDoDto(ToDo toDo) {

        return ToDoDto.builder()
                .title(toDo.getTitle())
                .createdAt(toDo.getCreatedAt().toString())
                .owner(toDo.getOwner().getFirstName())
                .build();

    }

    public ToDo makeToDoEntity(ToDoRequest toDoRequest) {

        return ToDo.builder()
                .title(toDoRequest.getTitle())
                .createdAt(Instant.now())
                .build();

    }

}
