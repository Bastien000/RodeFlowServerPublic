package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.RacerDTO;
import org.rodeflow.RodeFlowServer.entity.racer.RacerEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RacerMapper {
    List<RacerDTO> toDTO(List<RacerEntity> source);
    RacerDTO toDTO(RacerEntity source);
    RacerEntity toEntity(RacerDTO source);
}
