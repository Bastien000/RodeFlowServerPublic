package org.rodeflow.RodeFlowServer.service.helper;

import io.swagger.models.auth.In;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface HelperService {

    TimestampEntity createTimestamp();
    TeamEntity createTeam(String name);
    RacerEntity createRacer(String firstName, String lastName, String teamName);

    boolean uploadFile(MultipartFile file, Path path, String postID);

    boolean deleteFiles(String path) throws IOException;
}
