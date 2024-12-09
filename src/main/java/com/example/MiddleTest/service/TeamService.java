package com.example.MiddleTest.service;

import com.example.MiddleTest.entity.Team;
import com.example.MiddleTest.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // 모든 팀을 가져오는 메소드
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // 팀 이름으로 특정 팀을 찾는 메소드
    public Team getTeamByName(String teamName) {
        return teamRepository.findByName(teamName); // Optional을 처리해야 할 경우를 대비
    }
}
