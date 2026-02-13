package com.lms.lmsanasol.service;

import com.lms.lmsanasol.dto.CreateAdminRequest;
import com.lms.lmsanasol.entity.Role;
import com.lms.lmsanasol.entity.User;
import com.lms.lmsanasol.entity.UserSession;
import com.lms.lmsanasol.repository.UserRepository;
import com.lms.lmsanasol.repository.UserSessionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not activated");
        }

        if (user.getRole() == Role.STUDENT) {

            List<UserSession> activeSessions =
                    sessionRepository.findByUserAndActiveTrue(user);

            activeSessions.forEach(session -> session.setActive(false));

            sessionRepository.saveAll(activeSessions);
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        UserSession newSession = new UserSession();
        newSession.setUser(user);
        newSession.setJwtToken(token);
        newSession.setActive(true);
        newSession.setLoginTime(LocalDateTime.now());

        sessionRepository.save(newSession);

        return token;
    }

    public void createAdmin(CreateAdminRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User admin = new User();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);

        userRepository.save(admin);
    }
}
