package org.rodeflow.RodeFlowServer.entity.racer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "TeamEntity")
@Getter
@Setter
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamID;

    private String name;
    private int rankingScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "team_racer_id", joinColumns = @JoinColumn(name = "teamID"))
    @Column(name = "racerID")
    private Set<Integer> racerID = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "timestampID")
    private TimestampEntity timestamp;

    public void addRacer(Integer ID){
        this.racerID.add(ID);
    }
    public void removeRacer(Integer ID){
        this.racerID.remove(ID);
    }
}
