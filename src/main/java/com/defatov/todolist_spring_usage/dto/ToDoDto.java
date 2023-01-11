package com.defatov.todolist_spring_usage.dto;

import com.defatov.todolist_spring_usage.model.Task;
import com.defatov.todolist_spring_usage.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ToDoDto {

    @NotNull
    private String title;

    @NotNull
    @JsonProperty("created_at")
    private String createdAt;

    @NotNull
    private String owner;

}
