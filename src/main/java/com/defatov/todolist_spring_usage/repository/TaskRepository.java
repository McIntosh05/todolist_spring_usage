package com.defatov.todolist_spring_usage.repository;

import com.defatov.todolist_spring_usage.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{ 'todo_id' : { $todoId: ?0 } }")
    List<Task> getByTodoId(String todoId);

}
