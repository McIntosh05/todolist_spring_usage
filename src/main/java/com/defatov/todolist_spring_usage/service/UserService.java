package com.defatov.todolist_spring_usage.service;

import com.defatov.todolist_spring_usage.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(String id);
    User update(User user);
    void delete(String id);
    User findByEmail(String email);
    List<User> getAll();
}
