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
@RequestMapping("api/users/{user_id}")
public class ToDoController {

    private final ToDoService toDoService;

    private final UserService userService;

    private final ToDoDtoFactory toDoDtoFactory;

    private static final String TODO_ID = "/todos/{todo_id}";

    @Autowired
    public ToDoController(ToDoService toDoService, UserService userService, ToDoDtoFactory toDoDtoFactory) {
        this.toDoService = toDoService;
        this.userService = userService;
        this.toDoDtoFactory = toDoDtoFactory;
    }

    @GetMapping(path = "/todos")
    public ResponseEntity<List<ToDoDto>> getAllTodos(@PathVariable long user_id) {

        List<ToDoDto> todos = toDoService.getAll().stream()
                .filter(todo -> todo.getOwner().getId() == user_id)
                .map(toDoDtoFactory::makeToDoDto)
                .collect(Collectors.toList());

        return todos.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping(path = TODO_ID)
    public ResponseEntity<ToDoDto> getTodo(
            @PathVariable long todo_id,
            @PathVariable long user_id
    ) {

        ToDo toDo = toDoService.readById(todo_id);

        return new ResponseEntity<>(toDoDtoFactory.makeToDoDto(toDo), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<ToDoDto> create(@PathVariable long user_id, @RequestBody ToDoRequest toDoRequest) {

        ToDo toDo = toDoDtoFactory.makeToDoEntity(toDoRequest);
        toDo.setOwner(userService.readById(user_id));

        ToDo newToDo= toDoService.create(toDo);

        return newToDo != null ? new ResponseEntity<>(toDoDtoFactory.makeToDoDto(newToDo), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PatchMapping(path = TODO_ID)
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
            toDoService.update(newToDo);
            return new ResponseEntity<>(toDoDtoFactory.makeToDoDto(newToDo), HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
//
    @DeleteMapping(path = TODO_ID)
    public void delete(
            @PathVariable long todo_id,
            @PathVariable long user_id
    ) {

        toDoService.delete(todo_id);

    }

}
