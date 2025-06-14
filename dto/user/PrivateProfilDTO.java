package org.rodeflow.RodeFlowServer.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateProfilDTO {
    private Integer userID;
    private String type;
    private String userName;
    private String firstName;
    private String lastName;
    private String bioOnProfil;
}
