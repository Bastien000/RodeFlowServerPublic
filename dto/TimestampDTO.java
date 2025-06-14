package org.rodeflow.RodeFlowServer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimestampDTO {
    private Integer timestampID;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateAt;
    private int numberOfUpdates;

}