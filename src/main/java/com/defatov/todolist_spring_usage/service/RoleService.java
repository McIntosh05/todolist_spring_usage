package com.defatov.todolist_spring_usage.service;

import com.defatov.todolist_spring_usage.model.Role;

import java.util.List;


public interface RoleService {
    Role create(Role role);
    Role readById(long id);
    Role readByName(String name);
    Role update(Role role);
    void delete(long id);
    List<Role> getAll();
}
