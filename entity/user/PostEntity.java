package org.rodeflow.RodeFlowServer.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "PostEntity")
@Getter
@Setter


public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;

    private String name;
    private String type;
    private String description;
    private double longitude;
    private double latitude;
    private boolean isCloseFriend;
    private String fileName;

    private Integer userID;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_postReaction_id", joinColumns = @JoinColumn(name = "postID"))
    @Column(name = "postReactionID")
    private List<Integer> postReactionID = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "timestampID")
    private TimestampEntity timestamp;

    public void addPostReaction(Integer ID){
        this.postReactionID.add(ID);
    }
    public void removePostReaction(Integer ID){
        this.postReactionID.remove(ID);
    }

}
