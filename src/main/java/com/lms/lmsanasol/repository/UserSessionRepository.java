package com.lms.lmsanasol.repository;

import com.lms.lmsanasol.entity.User;
import com.lms.lmsanasol.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUserAndActiveTrue(User user);

    Optional<UserSession> findByJwtTokenAndActiveTrue(String jwtToken);

}
