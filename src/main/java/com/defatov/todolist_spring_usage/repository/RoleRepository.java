package com.defatov.todolist_spring_usage.repository;

import com.defatov.todolist_spring_usage.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role readByName(String name);
}
