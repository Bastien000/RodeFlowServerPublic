package org.rodeflow.RodeFlowServer.service.helper;


import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.rodeflow.RodeFlowServer.repository.TimestampRepository;
import org.rodeflow.RodeFlowServer.repository.racer.RacerRepository;
import org.rodeflow.RodeFlowServer.repository.racer.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HelperServiceImpl implements HelperService {

    @Autowired
    private final TimestampRepository timestampRepository;
    private final TeamRepository teamRepository;
    private final RacerRepository racerRepository;

    public HelperServiceImpl(TimestampRepository timestampRepository, TeamRepository teamRepository, RacerRepository racerRepository) {
        this.timestampRepository = timestampRepository;
        this.teamRepository = teamRepository;
        this.racerRepository = racerRepository;
    }


    public TimestampEntity createTimestamp(){
        TimestampEntity timestampEntity = new TimestampEntity();
        timestampEntity.setLastUpdateAt(LocalDateTime.now());
        timestampEntity.setCreatedAt(LocalDateTime.now());
        timestampEntity.setNumberOfUpdates(1);
        TimestampEntity result = timestampRepository.save(timestampEntity);
        System.out.println("HelperServiceImpl: createTimestamp");
        return result;
    }

    public TeamEntity createTeam(String name){
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(name);
        teamEntity.setRacerID(new HashSet<Integer>());
        teamEntity.setTimestamp(createTimestamp());
        TeamEntity result = teamRepository.save(teamEntity);
        System.out.println("HelperServiceImpl: createTeam");
        return result;
    }

    public RacerEntity createRacer(String firstName, String lastName, String teamName){
        RacerEntity racerEntity = new RacerEntity();
        racerEntity.setFirstName(firstName);
        racerEntity.setLastName(lastName);
        racerEntity.setTimestamp(createTimestamp());
        Optional<TeamEntity> optionalTeamEntity = teamRepository.findByName(teamName);
        if(optionalTeamEntity.isEmpty()){
            createTeam(teamName);
        }
        TeamEntity teamEntity = teamRepository.findByName(teamName).get();
        racerEntity.setTeamID(teamEntity.getTeamID());
        RacerEntity result = racerRepository.save(racerEntity);
        Set<Integer> racers = teamEntity.getRacerID();
        racers.add(result.getRacerID());
        teamEntity.setRacerID(racers);
        teamRepository.save(teamEntity);
        System.out.println("HelperServiceImpl: createRacer");
        return result;

    }



    @Override
    public boolean uploadFile(MultipartFile file, Path path, String postID) {
        try {
            if (file.isEmpty()) {
                return false;
            }
            Files.createDirectories(path);
            Path filePath = path.resolve(postID + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

/*
    @Override
    public boolean uploadFile(MultipartFile file, Path path, String postID){
        try {
            if (file.isEmpty()) {
                return false;
            }
            Files.createDirectories(path);
            Path filePath = Paths.get(path + "/" + postID + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
*/
    @Override
    public boolean deleteFiles(String path) throws IOException {
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File i : files) {
                    if (i.isFile()) {
                        i.delete();
                    }
                }
            }
            return true;
        } else {
            throw new IOException("Directory does not exist or is not a directory: " + path);
        }
    }





}
