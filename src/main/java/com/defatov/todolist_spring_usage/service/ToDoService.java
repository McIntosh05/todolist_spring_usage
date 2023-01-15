package com.defatov.todolist_spring_usage.service;

import com.defatov.todolist_spring_usage.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDo create(ToDo todo);
    ToDo readById(String id);
    ToDo update(ToDo todo);
    void delete(String id);

    List<ToDo> getAll();
    List<ToDo> getByUserId(String userId);
}
