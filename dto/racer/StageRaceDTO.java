package org.rodeflow.RodeFlowServer.dto.racer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.racer.StageEntity;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageRaceDTO {
    private Integer stageRaceID;

    private String name;
    private Integer gcWinnerID;
    private Integer sprintWinnerID;
    private Integer mountainWinnerID;
    private Integer youthWinnerID;

    private Set<Integer> stageID;

}
