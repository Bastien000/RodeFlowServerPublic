package org.rodeflow.RodeFlowServer.dto.mapper.racer;


import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.TeamDTO;
import org.rodeflow.RodeFlowServer.entity.racer.TeamEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    List<TeamDTO> toDTO(List<TeamEntity> source);
    TeamDTO toDTO(TeamEntity source);
    TeamEntity toEntity(TeamDTO source);
}
