package org.rodeflow.RodeFlowServer.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "PostReactionEntity")
@Getter
@Setter
public class PostReactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postReactionID;
    private PostReactionType type;
    private String value;

    private Integer postID;
    private Integer userID;


}

