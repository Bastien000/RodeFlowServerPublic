package org.rodeflow.RodeFlowServer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {

    private Integer newsID;
    private LocalDateTime timestamp;
    private String name;
    private String description;

    private String signature;


}
