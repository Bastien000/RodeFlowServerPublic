package org.rodeflow.RodeFlowServer.dto.racer;


import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacerDTO {

    private Integer racerID;

    private String nickName;
    private String firstName;
    private String lastName;
    private String gender;
    private String nationality;
    private String instagramLink;
    private String stravaLink;
    private String garminLink;
    private String bikeBrand;

    private int hillsScore;
    private int sprintScore;
    private int mountainScore;
    private int ittScore;
    private int gcScore;
    private int classicScore;
    private int downHillScore;
    private int cobblesScore;
    private int rankingPoint;

    private double weight;
    private double height;

    private LocalDateTime birth;

    private TimestampEntity timestamp;

    private Integer teamID;

    private List<Integer> ratingID;

}
