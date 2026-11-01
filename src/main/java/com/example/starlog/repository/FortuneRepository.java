package com.example.starlog.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.starlog.entity.Fortune;
import com.example.starlog.entity.User;

public interface FortuneRepository extends JpaRepository<Fortune, Long> {
    Optional<Fortune> findByUserAndFortuneDate(User user, LocalDate fortuneDate);
}
