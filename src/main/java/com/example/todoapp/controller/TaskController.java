package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    // Repozytorium zostanie tu automatycznie wstrzyknięte
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/tasks", params = { "!sort", "!page", "!size" })
    ResponseEntity<List<Task>> readAllTasks() {
        logger.info("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(path = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

}
