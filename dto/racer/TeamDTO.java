package org.rodeflow.RodeFlowServer.dto.racer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Integer teamID;
    private String name;
    private int rankingScore;
    private List<Integer> racerID;
    private TimestampEntity timestamp;


}
