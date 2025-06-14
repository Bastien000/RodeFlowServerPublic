package org.rodeflow.RodeFlowServer.dto.mapper;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.TimestampDTO;
import org.rodeflow.RodeFlowServer.entity.TimestampEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimestampMapper {
    List<TimestampDTO> toDTO(List<TimestampEntity> source);
    TimestampDTO toDTO(TimestampEntity source);
    TimestampEntity toEntity(TimestampDTO source);
}
