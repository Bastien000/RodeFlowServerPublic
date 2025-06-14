package org.rodeflow.RodeFlowServer.entity.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.time.LocalDateTime;
import java.util.*;


@Entity(name = "UserEntity")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    private String userName = null;
    @Column(nullable = false)
    private String email;
    private boolean isVerificated = false;
    private boolean isProfilPrivate = true;
    private boolean isOnline = false;

    private String firstName= null;
    private String lastName= null;
    private String bioOnProfil= null;
    private String phoneNumber= null;
    private String gender= null;
    private String nationality= null;
    private String instagramLink= null;
    private String stravaLink= null;
    private String garminLink= null;
    private String bikeBrand= null;
    private String publicKey= null;
    private String profilImageName = null;

    private int verificationCode = 0;
    private int hillsScore= 0;
    private int sprintScore= 0;
    private int mountainScore= 0;
    private int ittScore= 0;
    private int gcScore= 0;
    private int classicScore= 0;
    private int downHillScore= 0;
    private int cobblesScore= 0;
    private int rankingPoint= 0;

    private double weight= 0;
    private double height= 0;

    private LocalDateTime birth;

    private boolean isProRider;

    @OneToOne
    @JoinColumn(name = "timestampID")
    private TimestampEntity timestamp;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_post_id", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "postID")
    private List<Integer> postID = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_ride_id", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "rideID")
    private List<Integer> rideID = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_following_id", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "targetUserID")
    private List<Integer> followingID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_requested_following_id", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "targetUserID")
    private List<Integer> requestedFollowingID;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_close_friend_id", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "targetUserID")
    private List<Integer> closeFriendID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_collected_racer", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "racerID")
    private List<Integer> collectedRacerID = new ArrayList<>();

    public void addPost(Integer ID){
        this.postID.add(ID);
    }
    public void removePost(Integer ID){
        this.postID.remove(ID);
    }
    public void addCloseFriend(Integer ID){
        this.closeFriendID.add(ID);
    }
    public void removeCloseFriend(Integer ID){
        this.closeFriendID.remove(ID);
    }

    public void addRequestedFollowing(Integer ID){
        this.requestedFollowingID.add(ID);
    }
    public void removeRequestedFollowing(Integer ID){
        this.requestedFollowingID.remove(ID);
    }

    public void addFollowing(Integer ID){
        this.followingID.add(ID);
    }
    public void removeFollowing(Integer ID){
        this.followingID.remove(ID);
    }

    public void addRide(Integer ID){
        this.rideID.add(ID);
    }
    public void removeRide(Integer ID){
        this.rideID.remove(ID);
    }

    public void addCollectedRacer(Integer ID){
        this.collectedRacerID.add(ID);
    }
    public void removeCollectedRacer(Integer ID){
        this.collectedRacerID.remove(ID);
    }
}
