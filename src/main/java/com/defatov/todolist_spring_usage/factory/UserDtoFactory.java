package com.defatov.todolist_spring_usage.factory;

import com.defatov.todolist_spring_usage.dto.UserDto;
import com.defatov.todolist_spring_usage.dto.UserRequest;
import com.defatov.todolist_spring_usage.model.User;
import com.defatov.todolist_spring_usage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

    RoleService roleService;

    @Autowired
    public UserDtoFactory(RoleService roleService) {
        this.roleService = roleService;
    }

    public UserDto makeUserDto(User user) {

        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();

    }

    public User makeUserEntity(UserRequest userRequest) {

        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .role(roleService.readByName("USER"))
                .build();

    }

}
