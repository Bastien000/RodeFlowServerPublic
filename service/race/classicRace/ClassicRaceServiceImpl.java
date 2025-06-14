package org.rodeflow.RodeFlowServer.service.race.classicRace;


import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.rodeflow.RodeFlowServer.repository.racer.ClassicRaceRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RaceResultRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RacerRepository;
import org.rodeflow.RodeFlowServer.repository.racer.TeamRepository;
import org.rodeflow.RodeFlowServer.service.helper.HelperServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ClassicRaceServiceImpl implements ClassicRaceService{

    private final ClassicRaceRepository classicRaceRepository;
    private final TeamRepository teamRepository;
    private final RacerRepository racerRepository;
    private final HelperServiceImpl helperService;
    private final RaceResultRepository raceResultRepository;
    private final ResourceLoader resourceLoader;

    public ClassicRaceServiceImpl(ClassicRaceRepository classicRaceRepository, TeamRepository teamRepository, RacerRepository racerRepository, HelperServiceImpl helperService, RaceResultRepository raceResultRepository, ResourceLoader resourceLoader) {
        this.classicRaceRepository = classicRaceRepository;
        this.teamRepository = teamRepository;
        this.racerRepository = racerRepository;
        this.helperService = helperService;
        this.raceResultRepository = raceResultRepository;
        this.resourceLoader = resourceLoader;
    }

    public void createClassicRaceFromTxt(){
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("classicRaceData.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            System.out.println("ClassicRaceServiceImpl: createClassicRaceFromTxt-opening document");
            String headerLine = br.readLine();
            if (headerLine != null) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");

                    Optional<ClassicRaceEntity> classicRaceEntity = classicRaceRepository.findByName(Objects.equals(data[0], "null") ||data[0] == null || data[0].isEmpty() ? null : data[0]);
                    if(classicRaceEntity.isEmpty()){
                        classicRaceEntity = Optional.of(new ClassicRaceEntity());
                        classicRaceEntity.get().setName(data[0]);
                        classicRaceEntity.get().setLength(Objects.equals(data[1], "null") ||data[1] == null || data[1].isEmpty() ? 0 : Double.parseDouble(data[1]));
                        classicRaceEntity.get().setElevation(Objects.equals(data[2], "null") ||data[1] == null || data[2].isEmpty() ? 0 : Integer.parseInt(data[2]));
                        classicRaceEntity.get().setRaceResultID(new ArrayList<>());
                        classicRaceEntity.get().setVideoUrl(Objects.equals(data[7], "null") ||data[7] == null || data[7].isEmpty() ? null : data[7]);
                        String dateTimeString = Objects.equals(data[3], "null") ||data[3] == null || data[3].isEmpty() ? null : data[3];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

                        classicRaceEntity.get().setStartedAt(dateTime);
                        System.out.println("ClassicRaceServiceImpl: createClassicRaceFromTxt-creating classicRace");

                    }
                    RaceResultEntity raceResult = new RaceResultEntity();
                    Optional<TeamEntity> optionalTeamEntity = teamRepository.findByName(Objects.equals(data[5], "null") ||data[5] == null || data[5].isEmpty() ? null : data[5]);
                    TeamEntity team;
                    if(optionalTeamEntity.isEmpty()){
                        team = helperService.createTeam(data[5]);
                    }
                    else {
                        team = optionalTeamEntity.get();
                    }


                    String name = Objects.equals(data[4], "null") ||data[4] == null || data[4].isEmpty() ? null : data[4];
                    String[] names = name.split(" ");
                    String firstName = names[0];
                    String lastName = names[1];
                    for(int i = 2; i< names.length;i++ ){
                        lastName = lastName + " " + names[i];
                    }

                    Optional<RacerEntity> optionalRacerEntity = racerRepository.findByFirstNameAndLastNameAndTeamID(firstName,lastName, team.getTeamID());
                    RacerEntity racer;
                    if(optionalRacerEntity.isEmpty()){
                        racer = helperService.createRacer(firstName, lastName, data[5]);
                    }
                    else{
                        racer = optionalRacerEntity.get();
                    }
                    LocalDateTime dateTime = null;
                    String dateTimeString = Objects.equals(data[6], "null") ||data[6] == null || data[6].isEmpty() ? null : data[6];
                    System.out.println(dateTimeString);
                    if(dateTimeString!=null){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
                        dateTime = LocalDateTime.parse(dateTimeString, formatter);
                    }

                    raceResult.setRacerID(racer.getRacerID());
                    ClassicRaceEntity res = classicRaceRepository.save(classicRaceEntity.get());

                    raceResult.setFinishedAt(dateTime);
                    RaceResultEntity result = raceResultRepository.save(raceResult);

                   List<Integer> raceResultEntities = classicRaceEntity.get().getRaceResultID();
                    raceResultEntities.add(result.getRaceResultID());
                    classicRaceEntity.get().setRaceResultID(raceResultEntities);
                    classicRaceRepository.save(res);

                    System.out.println("ClassicRaceServiceImpl: createClassicRaceFromTxt-RaceResult "+ res.getRaceResultID() + "created");
                }

            }
        } catch (IOException e) {
            System.out.println("ClassicRaceServiceImpl: createClassicRaceFromTxt-Error");
            System.out.println(e.toString());
        }

    }

    @Override
    public Resource getRaceImageByID(Long classicRaceID){
        Optional<ClassicRaceEntity> classicRaceEntity = classicRaceRepository.findById(classicRaceID);
        if (classicRaceEntity.isEmpty()) {
            return null;
        }
        String location = "classpath:images/raceImages/" + classicRaceEntity.get().getName() + ".png";
        Resource imageResource = resourceLoader.getResource(location);
        if (!imageResource.exists()) {
            return null;
        }
        return imageResource;

    }
}
