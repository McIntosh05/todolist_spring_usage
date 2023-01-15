package com.defatov.todolist_spring_usage.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document(collection = "tasks")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    private String id;

    private String name;

    private Priority priority;

    private ToDo todo;

    private State state;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", todo=" + todo +
                ", state=" + state +
                '}';
    }
}
