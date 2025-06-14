package org.rodeflow.RodeFlowServer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostEntity;
import org.rodeflow.RodeFlowServer.entity.user.RideEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer userID;
    private String userName;
    private String email;
    private boolean isVerificated;
    private boolean isProfilPrivate;
    private boolean isOnline;

    private String firstName;
    private String lastName;
    private String bioOnProfil;
    private String phoneNumber;
    private String gender;
    private String nationality;
    private String instagramLink;
    private String stravaLink;
    private String garminLink;
    private String bikeBrand;
    private String publicKey;
    private String profilImageName;

    private int verificationCode;
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
    private List<Integer> requestedFollowingID;
    private List<Integer> closeFriendID;
    private List<Integer> collectedRacerID;
}
