package com.defatov.todolist_spring_usage.repository;

import com.defatov.todolist_spring_usage.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);
}
