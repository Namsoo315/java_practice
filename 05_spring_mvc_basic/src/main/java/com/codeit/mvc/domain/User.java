package com.codeit.mvc.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String roles;
    private List<String> devLang;
}
// lombok 사용
