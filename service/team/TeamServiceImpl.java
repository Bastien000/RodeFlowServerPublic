package org.rodeflow.RodeFlowServer.service.team;


import org.rodeflow.RodeFlowServer.dto.racer.TeamDTO;
import org.rodeflow.RodeFlowServer.dto.mapper.racer.TeamMapper;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.rodeflow.RodeFlowServer.repository.racer.TeamRepository;
import org.rodeflow.RodeFlowServer.service.helper.HelperServiceImpl;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class TeamServiceImpl  implements  TeamService{
    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;
    private final HelperServiceImpl helperService;

    public TeamServiceImpl(TeamMapper teamMapper, TeamRepository teamRepository, HelperServiceImpl helperService) {
        this.teamMapper = teamMapper;
        this.teamRepository = teamRepository;
        this.helperService = helperService;
    }


    public void createTeamFromTxt(){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("teamsData.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            System.out.println("TeamServiceImpl: createTeamFromTxt-opening document");

            String headerLine = br.readLine();
            if (headerLine != null) {
                String line;
                while ((line = br.readLine()) != null) {
                    TeamEntity team = new TeamEntity();
                    String[] data = line.split(";");

                    team.setName(Objects.equals(data[0], "null") ||data[0] == null || data[0].isEmpty() ? null : data[0]);
                    team.setRankingScore(Objects.equals(data[1], "null") ||data[1] == null || data[1].isEmpty() ? 0 : Integer.parseInt(data[1]));
                    team.setRacerID(new HashSet<Integer>());



                    TimestampEntity result = helperService.createTimestamp();
                    team.setTimestamp(result);
                    teamRepository.save(team);
                    System.out.println("TeamServiceImpl: createTeamFromTxt-Team: "+ data[0] + "created");
                }
            }
        } catch (IOException e) {
            System.out.println("TeamServiceImpl: createTeamFromTxt-Error");
            System.out.println(e.toString());
        }
    }


}
