package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.StageRaceDTO;
import org.rodeflow.RodeFlowServer.entity.racer.StageRaceEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StageRaceMapper {
    List<StageRaceDTO> toDTO(List<StageRaceEntity> source);
    StageRaceDTO toDTO(StageRaceEntity source);
    StageRaceEntity toEntity(StageRaceDTO source);
}
