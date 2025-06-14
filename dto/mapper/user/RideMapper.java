package org.rodeflow.RodeFlowServer.dto.mapper.user;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.RideDTO;
import org.rodeflow.RodeFlowServer.entity.user.RideEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {
    List<RideDTO> toDTO(List<RideEntity> source);
    RideDTO toDTO(RideEntity source);
    RideEntity toEntity(RideDTO source);
}
