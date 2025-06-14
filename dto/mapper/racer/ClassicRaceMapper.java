package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.ClassicRaceDTO;
import org.rodeflow.RodeFlowServer.entity.racer.ClassicRaceEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassicRaceMapper {
    List<ClassicRaceDTO> toDTO(List<ClassicRaceEntity> source);
    ClassicRaceDTO toDTO(ClassicRaceEntity source);
    ClassicRaceEntity toEntity(ClassicRaceDTO source);
}
