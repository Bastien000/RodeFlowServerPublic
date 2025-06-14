package org.rodeflow.RodeFlowServer.dto.racer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;
import org.rodeflow.RodeFlowServer.entity.racer.StageEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultDTO {
    private Integer raceResultID;

    private LocalDateTime finishedAt;
    private int racerRating;
    private Integer racerID;

    private List<Integer> ratingID;
}
