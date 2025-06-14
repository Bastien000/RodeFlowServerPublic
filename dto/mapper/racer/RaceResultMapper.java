package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.RaceResultDTO;
import org.rodeflow.RodeFlowServer.entity.racer.RaceResultEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RaceResultMapper {
    List<RaceResultDTO> toDTO(List<RaceResultEntity> source);
    RaceResultDTO toDTO(RaceResultEntity source);
    RaceResultEntity toEntity(RaceResultDTO source);
}
