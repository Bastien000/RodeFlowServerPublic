package org.rodeflow.RodeFlowServer.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "RideDataEntity")
@Getter
@Setter

public class RideDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rideDataID;

    private LocalDateTime timestamp;
    private double temperatureValue;
    private double speedValue;
    private int elevationValue;
    private int heartRateValue;
    private int powerValue;
    private int cadenceValue;

    private Integer rideID;
}
