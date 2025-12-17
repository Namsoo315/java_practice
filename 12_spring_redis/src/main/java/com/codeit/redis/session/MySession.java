package com.codeit.redis.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MySession implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private Instant createdAt;
}
