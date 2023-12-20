package com.example.baseballprediction.domain.team.service;

import com.example.baseballprediction.domain.team.dto.TeamResponse;
import com.example.baseballprediction.domain.team.entity.Team;
import com.example.baseballprediction.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.baseballprediction.domain.team.dto.TeamResponse.*;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<TeamsDTO> findTeams() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream().map(m -> new TeamsDTO(m)).toList();
    }
}
