package com.codeit.async.event;

import com.codeit.async.entiry.Product;

import java.io.File;
import java.util.List;
import java.util.UUID;

public record ProductCreatedEvent(
        Product product,
        List<File> files,
        UUID taskId
) {}