package com.example.MiddleTest.repository;

import com.example.MiddleTest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // 이메일 중복 확인 메서드 추가
}
