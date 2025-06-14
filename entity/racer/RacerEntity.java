package org.rodeflow.RodeFlowServer.entity.racer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "RacerEntity")
@Getter
@Setter
public class RacerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer racerID;

    private String nickName = null;
    private String firstName = null;
    private String lastName = null;
    private String gender = null;
    private String nationality = null;
    private String instagramLink = null;
    private String stravaLink= null;
    private String garminLink= null;
    private String bikeBrand= null;

    private int hillsScore = 0;
    private int sprintScore = 0;
    private int mountainScore = 0;
    private int ittScore = 0;
    private int gcScore = 0;
    private int classicScore = 0;
    private int downHillScore = 0;
    private int cobblesScore = 0;
    private int rankingPoint = 0;

    private double weight = 0;
    private double height = 0;

    private LocalDateTime birth;

    private Integer teamID;

    @OneToOne
    @JoinColumn(name = "timestampID")
    private TimestampEntity timestamp;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "racer_rating_id", joinColumns = @JoinColumn(name = "racerID"))
    @Column(name = "ratingID")
    private List<Integer> ratingID = new ArrayList<>();

    public void addRating(Integer ID){
        this.ratingID.add(ID);
    }
    public void removeRating(Integer ID){
        this.ratingID.remove(ID);
    }

}
