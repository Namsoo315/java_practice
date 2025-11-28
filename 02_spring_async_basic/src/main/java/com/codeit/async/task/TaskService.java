package com.codeit.async.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TaskService {

    private final Map<UUID, AsyncTask> tasks = new ConcurrentHashMap<>();

    public UUID createTask(String description) {
        UUID taskId = UUID.randomUUID();

        AsyncTask task = AsyncTask.builder()
                .taskId(taskId)
                .status(TaskStatus.PENDING)
                .description(description)
                .createdAt(Instant.now())
                .build();

        tasks.put(taskId, task);
        log.info("새 비동기 작업 생성 - taskId={}, description={}", taskId, description);
        return taskId;
    }

    public void markRunning(UUID taskId) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.setStatus(TaskStatus.RUNNING);
        }
    }

    public void markCompleted(UUID taskId) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.setStatus(TaskStatus.COMPLETED);
            task.setCompletedAt(Instant.now());
        }
    }

    public void markFailed(UUID taskId, String errorMessage) {
        AsyncTask task = tasks.get(taskId);
        if (task != null) {
            task.setStatus(TaskStatus.FAILED);
            task.setErrorMessage(errorMessage);
            task.setCompletedAt(Instant.now());
        }
    }

    public Optional<AsyncTask> getTask(UUID taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    public Collection<AsyncTask> getAllTasks() {
        return tasks.values();
    }
}
