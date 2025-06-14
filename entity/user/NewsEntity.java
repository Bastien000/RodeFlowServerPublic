package org.rodeflow.RodeFlowServer.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.time.LocalDateTime;

@Entity(name = "NewsEntity")
@Getter
@Setter

public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsID;
    private LocalDateTime timestamp;
    private String name;
    private String description;

    private String signature;




}