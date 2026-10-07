package com.spring.shortneer.service;

import com.spring.shortneer.dto.LoginRequestDto;
import com.spring.shortneer.entity.Admin;
import com.spring.shortneer.exception.InvalidCredentialsException;
import com.spring.shortneer.repository.AdminRepository;
import com.spring.shortneer.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String GENERIC_ERROR = "Invalid username or password";

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginRequestDto request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException(GENERIC_ERROR));

        boolean matches = passwordEncoder.matches(request.getPassword(), admin.getPassword());  // fill this

        if (!matches) {
            throw new InvalidCredentialsException(GENERIC_ERROR);
        }

        return jwtUtil.generateToken(admin.getUsername());
    }
}