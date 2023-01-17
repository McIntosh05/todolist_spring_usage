package com.defatov.todolist_spring_usage.controller;

import com.defatov.todolist_spring_usage.dto.TaskDto;
import com.defatov.todolist_spring_usage.dto.TaskRequest;
import com.defatov.todolist_spring_usage.factory.TaskDtoFactory;
import com.defatov.todolist_spring_usage.model.Task;
import com.defatov.todolist_spring_usage.service.StateService;
import com.defatov.todolist_spring_usage.service.TaskService;
import com.defatov.todolist_spring_usage.service.ToDoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users/{user_id}/todos/{todo_id}/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TaskDtoFactory taskDtoFactory;

    private final ToDoService toDoService;

    private final static String TASK_ID = "{task_id}";


    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll(
            @PathVariable String user_id,
            @PathVariable String todo_id
    ) {

        log.info("Searching all tasks in db by todo id " + todo_id);

        List<TaskDto> tasks = taskService.getAll().stream()
                .filter(task -> task.getTodo().getId().equals(todo_id))
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());

        return tasks.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(tasks, HttpStatus.OK);

    }


    @GetMapping(path = TASK_ID)
    public ResponseEntity<TaskDto> getTask(
            @PathVariable String user_id,
            @PathVariable String todo_id,
            @PathVariable String task_id
    ) {

        log.info("Getting task by id " + task_id);

        return new ResponseEntity<>(
                taskDtoFactory.makeTaskDto(taskService.readById(task_id)),
                HttpStatus.OK
        );

    }

    @PutMapping
    public ResponseEntity<TaskDto> create(
            @PathVariable String user_id,
            @PathVariable String todo_id,
            @RequestBody TaskRequest taskRequest
    ) {
        log.info("Creating new task with name" + taskRequest.getName());

        Task task = taskDtoFactory.makeTaskEntity(taskRequest);
        task.setTodo(toDoService.readById(todo_id));

        Task newTask = taskService.create(task);

        return newTask != null
                ? new ResponseEntity<>(taskDtoFactory.makeTaskDto(newTask), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PatchMapping(path = TASK_ID)
    public ResponseEntity<TaskDto> update(
            @PathVariable String user_id,
            @PathVariable String todo_id,
            @PathVariable String task_id,
            @RequestBody TaskRequest taskRequest
    ) {

        Task task = taskService.readById(task_id);

        if(task != null) {
            log.info("Updating task with id " + task_id);
            Task newTask = taskDtoFactory.makeTaskEntity(taskRequest);
            newTask.setId(task_id);
            newTask.setTodo(toDoService.readById(todo_id));
            taskService.update(newTask);
            return new ResponseEntity<>(taskDtoFactory.makeTaskDto(newTask), HttpStatus.OK);
        }

        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = TASK_ID)
    public void delete(
            @PathVariable String user_id,
            @PathVariable String todo_id,
            @PathVariable String task_id
    ) {

        taskService.delete(task_id);

        log.info("Task with id " + task_id + " was deleted");

    }

}
