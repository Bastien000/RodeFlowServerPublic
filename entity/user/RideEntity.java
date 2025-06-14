package org.rodeflow.RodeFlowServer.entity.user;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "RideEntity")
@Getter
@Setter
public class RideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rideID;

    private String name;
    private String description;
    private double rideRating;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private boolean isCloseFriend;
    private String note;
    private RideType type;

    private Integer userID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ride_postReaction_id", joinColumns = @JoinColumn(name = "rideID"))
    @Column(name = "postReactionID")
    private List<Integer> postReactionID = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ride_rideData_id", joinColumns = @JoinColumn(name = "rideID"))
    @Column(name = "rideDataID")
    private List<Integer> rideDataID = new ArrayList<>();

    public void addPostReaction(Integer ID){
        this.postReactionID.add(ID);
    }
    public void removePostReaction(Integer ID){
        this.postReactionID.remove(ID);
    }
    public void addRideData(Integer ID){
        this.rideDataID.add(ID);
    }
    public void removeRideData(Integer ID){
        this.rideDataID.remove(ID);
    }

}

