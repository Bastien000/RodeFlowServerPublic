package org.rodeflow.RodeFlowServer.dto.racer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceType;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassicRaceDTO {
    private Integer classicRaceID;

    private String name;
    private LocalDateTime startedAt;
    private double length;
    private int elevation;
    private String state;
    private String imageName;

    private ClassicRaceType type;
    private String videoUrl;


    private List<Integer> raceResultID;
}
