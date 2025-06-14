package org.rodeflow.RodeFlowServer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionEntity;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;

import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer postID;

    private String name;
    private String description;
    private Double longitude;
    private Double latitude;
    private boolean isCloseFriend;
    private String fileName;
    private String type;
    private Integer userID;

    private List<Integer> postReactionID;

    private TimestampEntity timestamp;

}
