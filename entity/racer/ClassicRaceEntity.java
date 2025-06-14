package org.rodeflow.RodeFlowServer.entity.racer;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "ClassicRaceEntity")
@Getter
@Setter
public class ClassicRaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classicRaceID;

    private String name;
    private LocalDateTime startedAt;
    private double length;
    private int elevation;
    private String state;
    private String imageName;
    private String videoUrl;

    private ClassicRaceType type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classicRace_raceResult_ID", joinColumns = @JoinColumn(name = "classicRaceID"))
    @Column(name = "raceResultID")
    private List<Integer> raceResultID = new ArrayList<>();

    public void addRaceResult(Integer ID){
        this.raceResultID.add(ID);
    }
    public void removeRaceResult(Integer ID){
        this.raceResultID.remove(ID);
    }
}

