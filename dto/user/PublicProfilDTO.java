package org.rodeflow.RodeFlowServer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicProfilDTO {

    private Integer userID;
    private String type;
    private String userName;
    private String firstName;
    private String lastName;
    private String bioOnProfil;
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

    private boolean isProRider;

    private TimestampEntity timestamp;
    private List<Integer> postID;
    private List<Integer> rideID;
    private List<Integer> followingID;
}
