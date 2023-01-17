package com.defatov.todolist_spring_usage.repository;

import com.defatov.todolist_spring_usage.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    Optional<State> findByName(String name);
}
