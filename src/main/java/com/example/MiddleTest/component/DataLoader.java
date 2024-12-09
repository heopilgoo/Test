package com.example.MiddleTest.component;

import com.example.MiddleTest.entity.Team;
import com.example.MiddleTest.repository.TeamRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoader {

    private final TeamRepository teamRepository;

    public DataLoader(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    public void init() {
        if (teamRepository.findByName("Arsenal") == null) {
            Team arsenal = new Team();
            arsenal.setName("Arsenal");
            arsenal.setImage("/images/premierleague_arsenal_132.png");
            arsenal.setFounded("1886");
            arsenal.setLocation("London");
            arsenal.setStadium("Emirates Stadium");
            arsenal.setLeagueTitles("13");
            arsenal.setFacupTitles("14");
            arsenal.setAchievements("Major achievements");
            arsenal.setDescription("Arsenal team description");
            arsenal.setLeagueName("Premier League");

            teamRepository.save(arsenal);
        }
    }
}
