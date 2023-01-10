package com.defatov.todolist_spring_usage.service;

import com.defatov.todolist_spring_usage.model.Task;

import java.util.List;

public interface TaskService {
    Task create(Task task);
    Task readById(long id);
    Task update(Task task);
    void delete(long id);

    List<Task> getAll();
    List<Task> getByTodoId(long todoId);
}
