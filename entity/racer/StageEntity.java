package org.rodeflow.RodeFlowServer.entity.racer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "StageEntity")
@Getter
@Setter
public class StageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stageID;

    private String name;
    private LocalDateTime startedAt;
    private double length;
    private int elevation;
    private StageType type;
    private String state;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "stage_midStageBonus_ID", joinColumns = @JoinColumn(name = "stageID"))
    @Column(name = "midStageBonusID")
    private Set<Integer> midStageBonusID = new HashSet<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "stageRace_raceResult_ID", joinColumns = @JoinColumn(name = "stageID"))
    @Column(name = "raceResultID")
    private Set<Integer> raceResultID = new HashSet<>();

    public void addMidStageBonus(Integer ID){
        this.midStageBonusID.add(ID);
    }
    public void removeMidStageBonus(Integer ID){
        this.midStageBonusID.remove(ID);
    }

    public void addRaceResult(Integer ID){
        this.raceResultID.add(ID);
    }
    public void removeRaceResult(Integer ID){
        this.raceResultID.remove(ID);
    }
}



