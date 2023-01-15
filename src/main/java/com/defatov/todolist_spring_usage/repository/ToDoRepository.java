package com.defatov.todolist_spring_usage.repository;

import com.defatov.todolist_spring_usage.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends MongoRepository<ToDo, String> {

    @Query("{ 'user_id' : { $userId: ?0 } }")
    List<ToDo> getByUserId(String userId);

}
