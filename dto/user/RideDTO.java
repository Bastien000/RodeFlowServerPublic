package org.rodeflow.RodeFlowServer.dto.user;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionEntity;
import org.rodeflow.RodeFlowServer.entity.user.RideDataEntity;
import org.rodeflow.RodeFlowServer.entity.user.RideType;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDTO {
    private Integer rideID;

    private String name;
    private String description;
    private double rideRating;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private boolean isCloseFriend;
    private String note;
    private RideType type;

    private UserEntity user;

    private List<Integer> postReactionID;

    private List<Integer> rideDataID;



}
