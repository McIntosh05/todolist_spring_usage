package com.defatov.todolist_spring_usage.controller;

import com.defatov.todolist_spring_usage.dto.UserDto;
import com.defatov.todolist_spring_usage.dto.UserRequest;
import com.defatov.todolist_spring_usage.exceptions.NullEntityReferenceException;
import com.defatov.todolist_spring_usage.factory.UserDtoFactory;
import com.defatov.todolist_spring_usage.model.User;
import com.defatov.todolist_spring_usage.service.RoleService;
import com.defatov.todolist_spring_usage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserDtoFactory userDtoFactory;

    @Autowired
    public UserController(UserService userService, RoleService roleService, UserDtoFactory userDtoFactory) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDtoFactory = userDtoFactory;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = userService.getAll().stream()
                .map(userDtoFactory::makeUserDto)
                .collect(Collectors.toList());

        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "{user_id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String user_id) {
        return new ResponseEntity<>(
                userDtoFactory.makeUserDto(userService.readById(user_id)),
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<UserDto> create(@RequestBody UserRequest userRequest) {

        boolean isPresent = userService.getAll().stream()
                .anyMatch(user -> user.getEmail().equals(userRequest.getEmail()));

        if(!isPresent) {
            User user = userDtoFactory.makeUserEntity(userRequest);
            user.setRole(roleService.readByName("USER"));
            userService.create(user);
            return new ResponseEntity<>(userDtoFactory.makeUserDto(user), HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping(path = "{user_id}")
    public ResponseEntity<UserDto> update(@PathVariable String user_id, @RequestBody UserRequest userRequest) {

        User user = userService.readById(user_id);

        if(user != null) {
            User newUser = userDtoFactory.makeUserEntity(userRequest);
            newUser.setId(user_id);
            userService.update(newUser);
            return new ResponseEntity<>(userDtoFactory.makeUserDto(newUser), HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "{user_id}")
    public void delete(@PathVariable String user_id) {
        userService.delete(user_id);
    }
}
