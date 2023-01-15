package com.defatov.todolist_spring_usage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class TaskDto {

    @NotNull
    private String name;

    @NotNull
    private String priority;

    @NotNull
    private String todo_name;

    @NotNull
    private String state;

}
