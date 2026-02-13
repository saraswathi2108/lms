package com.lms.lmsanasol.controller;

import com.lms.lmsanasol.dto.AuthResponse;
import com.lms.lmsanasol.dto.CreateAdminRequest;
import com.lms.lmsanasol.dto.LoginRequest;
import com.lms.lmsanasol.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lms")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new AuthResponse(token, "SUCCESS");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createAdmin(@Valid @RequestBody CreateAdminRequest request) {

        authService.createAdmin(request);

        return "Admin created successfully";
    }
}
