package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.RatingDTO;
import org.rodeflow.RodeFlowServer.entity.racer.RatingEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    List<RatingDTO> toDTO(List<RatingEntity> source);
    RatingDTO toDTO(RatingEntity source);
    RatingEntity toEntity(RatingDTO source);
}
