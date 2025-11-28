package com.codeit.async.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AsyncTask {
    private UUID taskId;
    private TaskStatus status;
    private String description;
    private String errorMessage;
    private Instant createdAt;
    private Instant completedAt;
}
