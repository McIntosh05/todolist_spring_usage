package com.defatov.todolist_spring_usage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


import java.util.List;

@Document(collection = "states")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class State {

    @Id
    private String id;

    private String name;

    private List<Task> tasks;

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
