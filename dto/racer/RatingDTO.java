package org.rodeflow.RodeFlowServer.dto.racer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Integer ratingID;
    private Integer userID;
    private Integer value;
    private Integer targetID;
}
