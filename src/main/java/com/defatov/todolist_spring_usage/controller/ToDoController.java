package com.defatov.todolist_spring_usage.controller;

import com.defatov.todolist_spring_usage.dto.ToDoDto;
import com.defatov.todolist_spring_usage.dto.ToDoRequest;
import com.defatov.todolist_spring_usage.exceptions.NullEntityReferenceException;
import com.defatov.todolist_spring_usage.factory.ToDoDtoFactory;
import com.defatov.todolist_spring_usage.model.ToDo;
import com.defatov.todolist_spring_usage.service.ToDoService;
import com.defatov.todolist_spring_usage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    private final UserService userService;

    private final ToDoDtoFactory toDoDtoFactory;

    @Autowired
    public ToDoController(ToDoService toDoService, UserService userService, ToDoDtoFactory toDoDtoFactory) {
        this.toDoService = toDoService;
        this.userService = userService;
        this.toDoDtoFactory = toDoDtoFactory;
    }

    @GetMapping(path = "users/{user_id}")
    public ResponseEntity<List<ToDoDto>> getAllTodos(@PathVariable long user_id) {

        List<ToDoDto> todos = toDoService.getAll().stream()
                .filter(todo -> todo.getOwner().getId() == user_id)
                .map(toDoDtoFactory::makeToDoDto)
                .collect(Collectors.toList());

        return todos.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping(path = "{todo_id}")
    public ResponseEntity<ToDoDto> readTodo(@PathVariable long todo_id) {

        ToDo toDo = toDoService.readById(todo_id);

        return new ResponseEntity<>(toDoDtoFactory.makeToDoDto(toDo), HttpStatus.OK);

    }

    @PutMapping(path = "/users/{user_id}")
    public ResponseEntity<ToDoDto> create(@PathVariable long user_id, @RequestBody ToDoRequest toDoRequest) {

        ToDo toDo = toDoDtoFactory.makeToDoEntity(toDoRequest);
        toDo.setOwner(userService.readById(user_id));

        ToDo newToDo= toDoService.create(toDo);

        return newToDo != null ? new ResponseEntity<>(toDoDtoFactory.makeToDoDto(newToDo), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PatchMapping(path = "/{todo_id}/users/{user_id}")
    public ResponseEntity<ToDoDto> update(
            @PathVariable long todo_id,
            @PathVariable long user_id,
            @RequestBody ToDoRequest toDoRequest
    ) {

        ToDo toDo = toDoService.readById(todo_id);

        if(toDo != null) {
            ToDo newToDo = toDoDtoFactory.makeToDoEntity(toDoRequest);
            newToDo.setId(todo_id);
            newToDo.setOwner(userService.readById(user_id));
            return new ResponseEntity<>(toDoDtoFactory.makeToDoDto(toDoService.update(newToDo)), HttpStatus.OK);
        }

        else {
            throw new NullEntityReferenceException("User cannot be null");
        }

    }

    @DeleteMapping(path = "{todo_id}/users/{user_id}")
    public void delete(
            @PathVariable long todo_id,
            @PathVariable long user_id
    ) {

        toDoService.delete(todo_id);

    }

}
