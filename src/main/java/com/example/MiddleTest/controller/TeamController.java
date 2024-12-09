package com.example.MiddleTest.controller;

import com.example.MiddleTest.entity.Team;
import com.example.MiddleTest.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 목록을 보여주는 페이지
    @GetMapping("/teams")
    public String showTeamsPage(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());  // 팀 목록을 모델에 담아서 전달
        return "teams/teamPage";  // teams/teamPage.mustache로 이동
    }

    // 아스날 상세 정보 페이지
    @GetMapping("/teams/arsenal")
    public String showArsenalDetails(Model model) {
        Team arsenal = teamService.getTeamByName("Arsenal");  // 아스날 팀 정보를 가져옴
        model.addAttribute("team", arsenal);  // 아스날 팀 정보를 템플릿에 전달
        return "teams/arsenal";  // teams/arsenal.mustache로 이동
    }
}
