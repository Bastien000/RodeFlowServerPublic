package org.rodeflow.RodeFlowServer.entity.racer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;

import java.util.Set;

@Entity(name = "MidStageBonusEntity")
@Getter
@Setter
public class MidStageBonusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
