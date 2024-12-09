package com.example.MiddleTest.repository;

import com.example.MiddleTest.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
    // 팀 이름으로 팀 정보를 조회
    Team findByName(String name);
}
