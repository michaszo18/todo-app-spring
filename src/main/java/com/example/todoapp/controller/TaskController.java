package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    // Repozytorium zostanie tu automatycznie wstrzyknięte
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.info("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTaskById(@PathVariable int id) {

        if (!repository.existsById(id)) {
            logger.error("GET: " + id);
            return ResponseEntity.notFound().build();
        }

        repository.findById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tasks")
    ResponseEntity<?> postTask(@RequestBody @Valid Task toSave) {

        repository.save(toSave);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {

        if (!repository.existsById(id)) {
            logger.error("PUT: " + toUpdate.getId());
            return ResponseEntity.notFound().build();
        }

        if (toUpdate.getId() != id) {
            logger.error("PUT: " + toUpdate.getId() + " put id is not equal with id");
        }

        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

}


