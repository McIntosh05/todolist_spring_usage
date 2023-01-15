package com.defatov.todolist_spring_usage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "todos")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ToDo {

    @Id
    private String id;

    private String title;

    private Instant createdAt = Instant.now();

    private User owner;

    private List<Task> tasks;

    private List<User> collaborators;

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", owner=" + owner +
                '}';
    }
}
