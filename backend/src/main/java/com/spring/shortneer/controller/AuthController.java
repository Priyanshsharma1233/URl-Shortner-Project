package com.spring.shortneer.controller;

import com.spring.shortneer.dto.LoginRequestDto;
import com.spring.shortneer.dto.LoginResponseDto;
import com.spring.shortneer.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        String token = authService.login(request);
        return new LoginResponseDto(token);
    }
}