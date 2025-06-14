package org.rodeflow.RodeFlowServer.entity.racer;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "RaceResultEntity")
@Getter
@Setter

public class RaceResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer raceResultID;

    private LocalDateTime finishedAt;
    private int racerRating = 0;

    private Integer racerID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "race_result_rating_id", joinColumns = @JoinColumn(name = "raceResultID"))
    @Column(name = "ratingID")
    private List<Integer> ratingID = new ArrayList<>();

    public void addRating(Integer ID){
        this.ratingID.add(ID);
    }
    public void removeRating(Integer ID){
        this.ratingID.remove(ID);
    }

}
