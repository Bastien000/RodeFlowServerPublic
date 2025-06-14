package org.rodeflow.RodeFlowServer.service.racer;

import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.rodeflow.RodeFlowServer.repository.racer.RacerRepository;
import org.rodeflow.RodeFlowServer.repository.racer.TeamRepository;
import org.rodeflow.RodeFlowServer.service.helper.HelperServiceImpl;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
public class RacerServiceImpl implements RacerService{
    private final RacerRepository racerRepository;
    private final HelperServiceImpl helperService;
    private final TeamRepository teamRepository;

    public RacerServiceImpl(RacerRepository racerRepository, HelperServiceImpl helperService, TeamRepository teamRepository){
        this.racerRepository = racerRepository;
        this.helperService = helperService;
        this.teamRepository = teamRepository;
    }

    public void createRacerFromTxt(){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("racersData.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            System.out.println("RacerServiceImpl: createRacerFormTxt-opening document");
            String headerLine = br.readLine();
            if (headerLine != null) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    RacerEntity racer = new RacerEntity();

                    racer.setNickName(Objects.equals(data[0], "null") ||data[0] == null || data[0].isEmpty() ? null : data[0]);
                    racer.setFirstName(Objects.equals(data[1], "null") ||data[1] == null || data[1].isEmpty() ? null : data[1]);
                    racer.setLastName(Objects.equals(data[2], "null") ||data[2] == null || data[2].isEmpty() ? null : data[2].strip());
                    racer.setGender(Objects.equals(data[3], "null") ||data[3] == null || data[3].isEmpty() ? null : data[3]);
                    racer.setNationality(Objects.equals(data[4], "null") ||data[4] == null || data[4].isEmpty() ? null : data[4]);
                    racer.setInstagramLink(Objects.equals(data[5], "null") ||data[5] == null || data[5].isEmpty() ? null : data[5]);
                    racer.setStravaLink(Objects.equals(data[6], "null") ||data[6] == null || data[6].isEmpty() ? null : data[6]);
                    racer.setGarminLink(Objects.equals(data[7], "null") ||data[7] == null || data[7].isEmpty() ? null : data[7]);
                    racer.setBikeBrand(Objects.equals(data[8], "null") ||data[8] == null || data[8].isEmpty() ? null : data[8]);
                    racer.setHillsScore(Objects.equals(data[9], "null") ||data[9] == null || data[9].isEmpty() ? 0 : Integer.parseInt(data[9]));
                    racer.setSprintScore(Objects.equals(data[10], "null") ||data[10] == null || data[10].isEmpty() ? 0 : Integer.parseInt(data[10]));
                    racer.setMountainScore(Objects.equals(data[11], "null") ||data[11] == null || data[11].isEmpty() ? 0 : Integer.parseInt(data[11]));
                    racer.setIttScore(Objects.equals(data[12], "null") ||data[12] == null || data[12].isEmpty() ? 0 : Integer.parseInt(data[12]));
                    racer.setGcScore(Objects.equals(data[13], "null") ||data[13] == null || data[13].isEmpty() ? 0 : Integer.parseInt(data[13]));
                    racer.setClassicScore(Objects.equals(data[14], "null") ||data[14] == null || data[14].isEmpty() ? 0 : Integer.parseInt(data[14]));
                    racer.setDownHillScore(Objects.equals(data[15], "null") ||data[15] == null || data[15].isEmpty() ? 0 : Integer.parseInt(data[15]));
                    racer.setCobblesScore(Objects.equals(data[16], "null") ||data[16] == null || data[16].isEmpty() ? 0 : Integer.parseInt(data[16]));
                    racer.setRankingPoint(Objects.equals(data[17], "null") ||data[17] == null || data[17].isEmpty() ? 0 : Integer.parseInt(data[17]));
                    racer.setWeight(Objects.equals(data[18], "null") ||data[18] == null || data[18].isEmpty() ? 0 : Double.parseDouble(data[18]));
                    racer.setHeight(Objects.equals(data[19], "null") ||data[19] == null || data[19].isEmpty() ? 0 : Double.parseDouble(data[19]));


                    String[] dateList = data[20].split("-");
                    LocalDateTime birth = LocalDateTime.of(Integer.parseInt(dateList[0]),Integer.parseInt(dateList[1]),Integer.parseInt(dateList[2]), 0 , 0);

                    racer.setBirth(birth);

                    TimestampEntity result = helperService.createTimestamp();
                    racer.setTimestamp(result);

                    Optional<TeamEntity> team = teamRepository.findByName(data[21]);
                    RacerEntity res = racerRepository.save(racer);
                    if(team.isEmpty()){
                        team = Optional.ofNullable(helperService.createTeam(data[21]));
                    }

                    res.setTeamID(team.get().getTeamID());
                    Set<Integer> racers = team.get().getRacerID();
                    racers.add(res.getRacerID());
                    team.get().setRacerID(racers);
                    teamRepository.save(team.get());
                    racerRepository.save(res);
                    System.out.println("RacerServiceImpl: createRacerFormTxt-Racer: " + data[1] + data[2] + "created.");
                }
            }
        } catch (IOException e) {
            System.out.println("RacerServiceImpl: createRacerFromTxt-Error");
            System.out.println(e.toString());
        }
    }







}
