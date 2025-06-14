package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.StageDTO;
import org.rodeflow.RodeFlowServer.entity.racer.StageEntity;


import java.util.List;

@Mapper(componentModel = "spring")
public interface StageMapper {
    List<StageDTO> toDTO(List<StageEntity> source);
    StageDTO toDTO(StageEntity source);
    StageEntity toEntity(StageDTO source);
}
