package org.rodeflow.RodeFlowServer.dto.racer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.racer.MidStageBonusEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;
import org.rodeflow.RodeFlowServer.entity.racer.StageRaceEntity;
import org.rodeflow.RodeFlowServer.entity.racer.StageType;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageDTO {
    private Integer stageID;

    private String name;
    private LocalDateTime startedAt;
    private double length;
    private int elevation;
    private StageType type;
    private String state;

    private Set<Integer> midStageBonusID;

    private Set<Integer> raceResultID;

    private Integer stageRaceID;
}
