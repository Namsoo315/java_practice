package com.codeit.async.controller;

import com.codeit.async.task.AsyncTask;
import com.codeit.async.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<AsyncTask> getTask(@PathVariable String taskId) {
        UUID uuid = UUID.fromString(taskId);
        return taskService.getTask(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<AsyncTask>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
}

