package com.codeit.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private LocalDate birthday;
    private Instant createdAt;
    private Instant updatedAt;
}