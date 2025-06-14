package org.rodeflow.RodeFlowServer.entity.racer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "RatingEntity")
@Getter
@Setter

public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingID;
    private Integer userID;
    private Integer value;
    private Integer targetID;
}
