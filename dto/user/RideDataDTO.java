package org.rodeflow.RodeFlowServer.dto.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.user.RideEntity;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDataDTO {
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
