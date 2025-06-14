package org.rodeflow.RodeFlowServer.dto.racer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.racer.MidStageBonusType;
import org.rodeflow.RodeFlowServer.entity.racer.StageEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MidStageBonusDTO {
    private Integer midStageBonusID;

    private MidStageBonusType type;
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private int value5;
    private int value6;
    private int value7;
    private int value8;
    private int value9;
    private int value10;

    private Integer stageID;
}
