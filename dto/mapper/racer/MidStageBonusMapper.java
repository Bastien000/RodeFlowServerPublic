package org.rodeflow.RodeFlowServer.dto.mapper.racer;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.racer.MidStageBonusDTO;
import org.rodeflow.RodeFlowServer.entity.racer.MidStageBonusEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MidStageBonusMapper {
    List<MidStageBonusDTO> toDTO(List<MidStageBonusEntity> source);
    MidStageBonusDTO toDTO(MidStageBonusEntity source);
    MidStageBonusEntity toEntity(MidStageBonusDTO source);
}
