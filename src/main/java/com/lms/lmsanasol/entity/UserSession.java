package com.lms.lmsanasol.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
@Data
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 1000, nullable = false)
    private String jwtToken;

    private boolean active = true;

    private LocalDateTime loginTime;

    @PrePersist
    protected void onLogin() {
        loginTime = LocalDateTime.now();
    }
}
