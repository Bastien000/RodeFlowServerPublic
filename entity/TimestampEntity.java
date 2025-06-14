package org.rodeflow.RodeFlowServer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity(name = "TimestampEntity")
@Setter
@Getter
public class TimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timestampID;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateAt;
    private int numberOfUpdates;

}
