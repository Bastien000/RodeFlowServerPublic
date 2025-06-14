package org.rodeflow.RodeFlowServer.dto.mapper.user;
import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.RideDataDTO;
import org.rodeflow.RodeFlowServer.entity.user.RideDataEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideDataMapper {
    List<RideDataDTO> toDTO(List<RideDataEntity> source);
    RideDataDTO toDTO(RideDataEntity source);
    RideDataEntity toEntity(RideDataDTO source);
}
