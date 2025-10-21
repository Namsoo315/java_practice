package com.codeit.batch.controller;

import com.codeit.batch.dto.SystemLoginStatDto;
import com.codeit.batch.service.LoginStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class LoginStatController {

    private final LoginStatService loginStatService;

    @GetMapping("/login")
    public ResponseEntity<SystemLoginStatDto> getSystemStats() {
        SystemLoginStatDto dto = loginStatService.getSystemStats();
        return ResponseEntity.ok(dto);
    }
}
