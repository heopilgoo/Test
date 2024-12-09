package com.example.MiddleTest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {

    @Id
    private String name; // 팀 이름

    private String image; // 팀 이미지 파일명

    private String founded; // 창단 연도

    private String location; // 연고지

    private String stadium; // 홈구장

    private String leagueTitles; // 리그 우승 횟수

    private String facupTitles; // FA컵 우승 횟수

    private String achievements; // 기타 주요 성과

    private String description; // 구단 소개

    private String leagueName; // 리그 이름
}
