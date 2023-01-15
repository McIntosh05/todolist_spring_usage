package com.defatov.todolist_spring_usage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


import java.util.List;

@Document(collection = "roles")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Role {

    @Id
    private String id;

    private String name;

//    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
