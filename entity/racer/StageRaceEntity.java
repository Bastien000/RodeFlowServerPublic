package org.rodeflow.RodeFlowServer.entity.racer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "StageRaceEntity")
@Getter
@Setter
public class StageRaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stageRaceID;

    private String name;
    private Integer gcWinnerID;
    private Integer sprintWinnerID;
    private Integer mountainWinnerID;
    private Integer youthWinnerID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "stageRace_stage_ID", joinColumns = @JoinColumn(name = "stageRaceID"))
    @Column(name = "stageID")
    private Set<Integer> stageID = new HashSet<>();

    public void addStage(Integer ID){
        this.stageID.add(ID);
    }
    public void removeStage(Integer ID){
        this.stageID.remove(ID);
    }
}
