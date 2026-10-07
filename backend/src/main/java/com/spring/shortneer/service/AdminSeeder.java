package com.spring.shortneer.service;

import com.spring.shortneer.entity.Admin;
import com.spring.shortneer.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.default-password}")
    private String defaultPassword;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            adminRepository.save(admin);
            System.out.println("Seeded default admin account");
        }
    }
}