package org.rodeflow.RodeFlowServer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.user.PostEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionType;
import org.rodeflow.RodeFlowServer.entity.user.RideEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReactionDTO {
    private int postReactionID;
    private PostReactionType type;
    private String value;

    private Integer postID;
    private Integer userID;


}
