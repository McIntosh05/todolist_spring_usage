package com.defatov.todolist_spring_usage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String priority;

}
